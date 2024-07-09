package com.ucoban.medsoft.appointmentserver.dao.client;

import com.ucoban.medsoft.appointmentserver.dto.AccountDto;
import com.ucoban.medsoft.appointmentserver.dto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "accountms", qualifiers = "accountFeignClient")
public interface IAccountFeignClient {
	
	@GetMapping("/api/{id}")
	ResponseEntity<ApiResponseDto<AccountDto>> getAccountById(@PathVariable("id") String id) ;
	
}
