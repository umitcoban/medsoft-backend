package com.ucoban.medsoft.accountserver.controller;

import com.ucoban.medsoft.accountserver.dao.client.IDocumentFeignClient;
import com.ucoban.medsoft.accountserver.dao.service.IAccountService;
import com.ucoban.medsoft.accountserver.dto.*;
import com.ucoban.medsoft.accountserver.mapper.IAccountMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final IAccountService accountService;

    private final IAccountMapper accountMapper;

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final IDocumentFeignClient documentFeignClient;

    public AccountController(
            @Qualifier("accountServiceImpl") IAccountService accountService,
            IAccountMapper accountMapper,
            IDocumentFeignClient documentFeignClient) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.documentFeignClient = documentFeignClient;
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDto<String>> createNewAccount(@Valid @RequestBody RegisterDto registerDto){
        accountService.create(registerDto);
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), "Account created successfully!", HttpStatus.CREATED.value()));
    }

    @PutMapping()
    public ResponseEntity<ApiResponseDto<AccountDto>> updateAccount(@Valid @RequestBody UpdateDto updateDto, @RequestHeader() HttpHeaders headers){
        var userId = headers.getFirst("user-id");
        logger.info("userID: {}", userId);
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountMapper.accountDtoToAccount(accountService.update(updateDto, userId), documentFeignClient), HttpStatus.CREATED.value()));
    }

    @GetMapping("/myAccount")
    public ResponseEntity<ApiResponseDto<AccountDto>> getAccount(@RequestHeader() HttpHeaders headers) {
        var userId = headers.getFirst("user-id");
        logger.info("userID: {}", userId);;
        var accountDto = accountMapper.accountDtoToAccount(accountService.findById(userId), documentFeignClient);
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountDto , HttpStatus.FOUND.value()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AccountDto>> getAccountById(@RequestHeader() HttpHeaders headers, @PathVariable("id") String id) {
        var userId = headers.getFirst("user-id");
        logger.info("request userID: {}, id: {}", userId, id);
        var accountDto = accountMapper.accountDtoToAccount(accountService.findById(id), documentFeignClient);
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountDto , HttpStatus.FOUND.value()));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AccountDto>> updateAccountWithId(@Valid @RequestBody UpdateDto updateDto, @RequestHeader() HttpHeaders headers, @PathVariable("id") String id){
        var userId = headers.getFirst("user-id");
        logger.info("request userID: {} id: {}", userId, id);
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountMapper.accountDtoToAccount(accountService.update(updateDto, id), documentFeignClient), HttpStatus.CREATED.value()));
    }
    
    @PostMapping("/{id}/updateRole")
    public ResponseEntity<ApiResponseDto<Boolean>> updateAccountRole(@Valid @RequestBody UpdateRoleDto updateRoleDto, @PathVariable("id") String id){
        accountService.updateAccountRole(updateRoleDto, id);
        return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), true, HttpStatus.OK.value()));
    }
    
    @PostMapping("/{id}/changePassword")
    public ResponseEntity<ApiResponseDto<Boolean>> updateAccountRole(@Valid @NotBlank @RequestBody PasswordDto passwordDto, @PathVariable("id") String id){
        logger.info("request userID: {} password: {}", id, passwordDto.password());
        return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), accountService.changePassword(id, passwordDto.password()), HttpStatus.OK.value()));
    }
    
    @GetMapping("/findAllAccount")
    public ResponseEntity<ApiResponseDto<Page<AccountDto>>> getAccountsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortOrder = Sort.by(direction, sort[0]);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<AccountDto> accountPage = accountService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), accountPage, HttpStatus.OK.value()));
    }

}
