package com.ucoban.medsoft.accountserver.controller;


import com.ucoban.medsoft.accountserver.dao.service.IAccountService;
import com.ucoban.medsoft.accountserver.dto.AccountAnalyticsDto;
import com.ucoban.medsoft.accountserver.dto.ApiResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AccountAnalyticsController {

    private IAccountService accountService;

    public AccountAnalyticsController(@Qualifier("accountServiceImpl") IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/counts")
    public ResponseEntity<ApiResponseDto<AccountAnalyticsDto>> getAccountAnalytics() {
        return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), accountService.findAccountsAnalyticsDto(), HttpStatus.OK.value()));
    }
}
