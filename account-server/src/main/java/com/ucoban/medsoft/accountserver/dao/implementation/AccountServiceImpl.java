package com.ucoban.medsoft.accountserver.dao.implementation;

import com.ucoban.medsoft.accountserver.dao.client.IDocumentFeignClient;
import com.ucoban.medsoft.accountserver.dao.service.IAccountService;
import com.ucoban.medsoft.accountserver.dao.service.IKeyCloakService;
import com.ucoban.medsoft.accountserver.dto.AccountAnalyticsDto;
import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.entity.Account;
import com.ucoban.medsoft.accountserver.entity.ERole;
import com.ucoban.medsoft.accountserver.entity.Role;
import com.ucoban.medsoft.accountserver.mapper.IAccountMapper;
import com.ucoban.medsoft.accountserver.repository.IAccountRepository;
import jakarta.transaction.Transactional;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("accountServiceImpl")
public class AccountServiceImpl implements IAccountService {
    @Value("${keycloak-admin.client-id}")
    private String clientId;

    private final IAccountRepository accountRepository;

    private final IKeyCloakService keyCloakService;

    private final IAccountMapper accountMapper;

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(
            IAccountRepository accountRepository,
                              @Qualifier("keyCloakServiceImpl") IKeyCloakService keyCloakService,
           IAccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.keyCloakService = keyCloakService;
        this.accountMapper = accountMapper;
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
        var defaultRoles = new HashSet<Role>();
        defaultRoles.add(new Role(ERole.USER));
        account.setRoles(defaultRoles);
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
}
