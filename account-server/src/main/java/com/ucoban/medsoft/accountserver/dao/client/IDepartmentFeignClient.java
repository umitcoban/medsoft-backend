package com.ucoban.medsoft.accountserver.dao.client;

import com.ucoban.medsoft.accountserver.dto.ApiResponseDto;
import com.ucoban.medsoft.accountserver.dto.AssignDepartmentDto;
import com.ucoban.medsoft.accountserver.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "departmentms")
public interface IDepartmentFeignClient {
	
	@PostMapping(value = "/api/assign", consumes = "application/json")
	ResponseEntity<ApiResponseDto<String>> assignNewDepartment(@RequestBody AssignDepartmentDto assignDepartmentDto);
	
	@GetMapping("/api/users/{userId}")
	ResponseEntity<ApiResponseDto<List<DepartmentDto>>> getDepartmentsByUserId(@PathVariable("userId") String userId);
}
