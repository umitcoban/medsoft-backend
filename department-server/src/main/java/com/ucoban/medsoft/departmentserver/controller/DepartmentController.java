package com.ucoban.medsoft.departmentserver.controller;


import com.ucoban.medsoft.departmentserver.dao.DepartmentService;
import com.ucoban.medsoft.departmentserver.dto.ApiResponseDto;
import com.ucoban.medsoft.departmentserver.dto.AssignDepartmentDto;
import com.ucoban.medsoft.departmentserver.dto.DepartmentDto;
import com.ucoban.medsoft.departmentserver.mapper.DepartmentMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;
	private final DepartmentMapper departmentMapper;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto<List<DepartmentDto>>> findAll() {
		var departments = departmentMapper.departmentToDepartmentDtoList(departmentService.getAllDepartments());
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), departments, HttpStatus.OK.value()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<DepartmentDto>> findById(@PathVariable("id") Long id) {
		var department = departmentMapper.departmentToDepartmentDto(departmentService.getDepartmentById(id));
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), department, HttpStatus.OK.value()));
	}
	
	@PostMapping()
	public ResponseEntity<ApiResponseDto<String>> createDepartment(@RequestBody DepartmentDto departmentDto) {
		departmentService.addDepartment(departmentMapper.departmentDtoToDepartment(departmentDto));
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), "Department created successfully", HttpStatus.OK.value()));
	}
	
	@PostMapping("/assign")
	public ResponseEntity<ApiResponseDto<String>> assignDepartment(@Valid @RequestBody AssignDepartmentDto assignDepartmentDto) {
		departmentService.assignDepartment(assignDepartmentDto);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), "Department assigned successfully", HttpStatus.OK.value()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDto<String>> deleteDepartment(@PathVariable("id") Long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), "Department deleted successfully", HttpStatus.OK.value()));
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ApiResponseDto<List<DepartmentDto>>> getDepartmentsByUserId(@PathVariable("userId") String userId) {
		return ResponseEntity.ok(new ApiResponseDto<>(
				System.currentTimeMillis(),
				departmentMapper.departmentToDepartmentDtoList(departmentService.getDepartmentsByUserId(userId)
				), HttpStatus.OK.value()));
	}
	
}
