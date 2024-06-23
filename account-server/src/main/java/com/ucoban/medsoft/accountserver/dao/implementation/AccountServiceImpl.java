package com.ucoban.medsoft.accountserver.dao.implementation;

import com.ucoban.medsoft.accountserver.dao.client.IDocumentFeignClient;
import com.ucoban.medsoft.accountserver.dao.service.IAccountService;
import com.ucoban.medsoft.accountserver.dao.service.IKeyCloakService;
import com.ucoban.medsoft.accountserver.dto.*;
import com.ucoban.medsoft.accountserver.entity.Account;
import com.ucoban.medsoft.accountserver.entity.ERole;
import com.ucoban.medsoft.accountserver.entity.Role;
import com.ucoban.medsoft.accountserver.mapper.IAccountMapper;
import com.ucoban.medsoft.accountserver.repository.IAccountRepository;
import com.ucoban.medsoft.accountserver.repository.IRoleRepository;
import jakarta.transaction.Transactional;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("accountServiceImpl")
public class AccountServiceImpl implements IAccountService {
    private final IDocumentFeignClient iDocumentFeignClient;
    @Value("${keycloak-admin.client-id}")
    private String clientId;

    private final IAccountRepository accountRepository;

    private final IKeyCloakService keyCloakService;

    private final IAccountMapper accountMapper;
    
    private final IRoleRepository roleRepository;

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(
            IAccountRepository accountRepository,
            @Qualifier("keyCloakServiceImpl") IKeyCloakService keyCloakService,
            IAccountMapper accountMapper, IDocumentFeignClient iDocumentFeignClient, IRoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.keyCloakService = keyCloakService;
        this.accountMapper = accountMapper;
        this.iDocumentFeignClient = iDocumentFeignClient;
        this.roleRepository = roleRepository;
    }

    @Override
    public Account findById(String id) {
        var account = accountRepository.findById(id);
        return account.orElseThrow();
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    public void create(RegisterDto registerDto) {
        var account = accountMapper.registerDtoToAccount(registerDto);
        var roles = roleRepository.findAll();
        account.setRoles(roles.stream().filter(role -> role.getRole() == ERole.USER).collect(Collectors.toSet()));
        keyCloakService.addNewRole(ERole.USER.name());
        account.setId(createAndKeyCloakGetUserId(registerDto));
        accountRepository.save(account);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Account update(UpdateDto updateDto, String userId){
        var optAccount = accountRepository.findById(userId);
        if(optAccount.isPresent()){
            var account = optAccount.get();
            account.setHeight(updateDto.height());
            account.setWeight(updateDto.weight());
            account.setBirthDate(updateDto.birthDate());
            account.setEmail(updateDto.email());
            account.setFirstName(updateDto.firstName());
            account.setLastName(updateDto.lastName());
            account.setPhone(updateDto.phone());
            return accountRepository.save(account);
        }
        throw new RuntimeException();
    }

    @Override
    public AccountAnalyticsDto findAccountsAnalyticsDto() {
        var accountWithRolesCount = accountRepository.findAccountRoleAndCounts();
        var accountsCountWithDate = accountRepository.findAccountCountWithDate().stream().map(v -> Pair.of((Long) v[0], (Date) v[1])).toList();
        return new AccountAnalyticsDto(accountWithRolesCount, accountsCountWithDate);
    }

    @Transactional
    protected String createAndKeyCloakGetUserId(RegisterDto registerDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(registerDto.email());
        userRepresentation.setFirstName(registerDto.firstName());
        userRepresentation.setLastName(registerDto.lastName());
        userRepresentation.setEmail(registerDto.email());
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("origin", List.of("demo"));
        attributes.put("phone", List.of(registerDto.phone()));
        userRepresentation.setAttributes(attributes);
        var keyCloakUserId = keyCloakService.findUserIdByResponse(keyCloakService.createUser(userRepresentation));
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(registerDto.password());
        var userResource = keyCloakService.getUsersResource();
        var keyCloakUser = userResource.get(keyCloakUserId);
        logger.info("keyCloakUser: {}", keyCloakUser.toRepresentation().getUsername());
        keyCloakService.resetUserPassword(userResource.get(keyCloakUserId), passwordCred);
        var client = keyCloakService.getClientByClientId(clientId);
        logger.info("client Id: {}, client clientId: {}", client.getId(), client.getClientId());
        var userRole = keyCloakService.rolesResourceByClientId(client.getId()).get(ERole.USER.name()).toRepresentation();
        logger.info("keyCloakUser clientLevel {}", keyCloakUser.roles().clientLevel(client.getId()).listAll());
        keyCloakUser.roles().clientLevel(client.getId()).add(List.of(userRole));
        return keyCloakUserId;
    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts.map(acc -> accountMapper.accountDtoToAccount(acc, iDocumentFeignClient));
    }

    @Override
    public List<AccountDto> findAll(Sort sort) {
        return List.of();
    }
    
    @Transactional
    @Override
    public void updateAccountRole(UpdateRoleDto updateRoleDto, String userId) {
        var roles = roleRepository.findAll().stream()
                .filter(role -> Arrays.stream(updateRoleDto.ids()).anyMatch(val -> role.getId() == val))
                .collect(Collectors.toSet());
        var account = findById(userId);
        
        roles.forEach(role -> keyCloakService.addNewRole(role.getRole().name()));

        List<String> existingRoles = keyCloakService.getUserRoles(userId);

        roles.forEach(role -> {
            if (!existingRoles.contains(role.getRole().name())) {
                keyCloakService.addRoleToUser(userId, role.getRole().name());
            }
        });

        existingRoles.forEach(existingRole -> {
            if (roles.stream().noneMatch(role -> role.getRole().name().equals(existingRole))) {
                keyCloakService.removeRoleFromUser(userId, existingRole);
            }
        });

        account.setRoles(roles);
        accountRepository.save(account);
    }
    
    @Override
    public boolean changePassword(String userId, String newPassword) {
        var account = findById(userId);
        keyCloakService.changePassword(account.getId(), newPassword);
        return true;
    }
}
