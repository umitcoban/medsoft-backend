package com.ucoban.medsoft.accountserver.controller;


import com.ucoban.medsoft.accountserver.dao.service.IRoleService;
import com.ucoban.medsoft.accountserver.dto.ApiResponseDto;
import com.ucoban.medsoft.accountserver.dto.RoleDto;
import com.ucoban.medsoft.accountserver.mapper.IRoleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	private final IRoleService roleService;
	private final IRoleMapper roleMapper;
	
	
	public RoleController(IRoleService roleService, IRoleMapper roleMapper) {
		this.roleService = roleService;
		this.roleMapper = roleMapper;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponseDto<List<RoleDto>>> getAllRoles() {
		var roles = roleService.getAllRoles();
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), roleMapper.rolesToDtos(roles), HttpStatus.OK.value()));
	}
	
}
