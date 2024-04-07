package com.ucoban.medsoft.accountserver.controller;

import com.ucoban.medsoft.accountserver.dao.service.IAccountService;
import com.ucoban.medsoft.accountserver.dto.AccountDto;
import com.ucoban.medsoft.accountserver.dto.ApiResponseDto;
import com.ucoban.medsoft.accountserver.dto.RegisterDto;
import com.ucoban.medsoft.accountserver.dto.UpdateDto;
import com.ucoban.medsoft.accountserver.mapper.IAccountMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public AccountController(@Qualifier("accountServiceImpl") IAccountService accountService, IAccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
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
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountMapper.accountDtoToAccount(accountService.update(updateDto, userId)), HttpStatus.CREATED.value()));
    }

    @GetMapping("/findAccountById")
    public ResponseEntity<ApiResponseDto<AccountDto>> getAccount(@RequestHeader() HttpHeaders headers) {
        var userId = headers.getFirst("user-id");
        logger.info("userID: {}", userId);
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountMapper.accountDtoToAccount(accountService.findById(userId)), HttpStatus.FOUND.value()));
    }

    @GetMapping("/findAllAccount")
    public ResponseEntity<ApiResponseDto<List<AccountDto>>> getAccounts() {
        return ResponseEntity.accepted().body(new ApiResponseDto<>(System.currentTimeMillis(), accountMapper.accountListToAccountDtoList(accountService.findAll()), HttpStatus.FOUND.value()));
    }

}
