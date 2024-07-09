package com.ucoban.medsoft.appointmentserver.controller;

import com.ucoban.medsoft.appointmentserver.dao.IAppointmentService;
import com.ucoban.medsoft.appointmentserver.dao.client.IAccountFeignClient;
import com.ucoban.medsoft.appointmentserver.dto.ApiResponseDto;
import com.ucoban.medsoft.appointmentserver.dto.AppointmentCreateDto;
import com.ucoban.medsoft.appointmentserver.dto.AppointmentDto;
import com.ucoban.medsoft.appointmentserver.mapper.IAppointmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AppointmentController {

	private final IAppointmentService appointmentServiceImpl;
	private final IAppointmentMapper appointmentMapper;
	private  final IAccountFeignClient accountFeignClient;
	
	@GetMapping
	public ResponseEntity<ApiResponseDto<List<AppointmentDto>>> getAllAppointments() {
		var result = appointmentMapper.toAppointmentDtoList(appointmentServiceImpl.getAppointments(), accountFeignClient);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), result, HttpStatus.OK.value()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<AppointmentDto>> getAllAppointmentById(@PathVariable Long id) {
		var result = appointmentMapper.toAppointmentDto(appointmentServiceImpl.getAppointment(id), accountFeignClient);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), result, HttpStatus.OK.value()));
	}
	
	@GetMapping("/patients/{id}")
	public ResponseEntity<ApiResponseDto<List<AppointmentDto>>> getAllAppointmentsByPatientId(@PathVariable String id) {
		var result = appointmentMapper.toAppointmentDtoList(appointmentServiceImpl.findByPatientId(id), accountFeignClient);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), result, HttpStatus.OK.value()));
	}
	
	@GetMapping("/doctors/{id}")
	public ResponseEntity<ApiResponseDto<List<AppointmentDto>>> getAllAppointmentsByDoctorId(@PathVariable String id) {
		var result = appointmentMapper.toAppointmentDtoList(appointmentServiceImpl.findByDoctorId(id), accountFeignClient);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), result, HttpStatus.OK.value()));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDto<String>> addNewAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto) {
		var appointment = appointmentMapper.toAppointment(appointmentCreateDto);
		appointmentServiceImpl.addAppointment(appointment);
		return ResponseEntity.ok(new ApiResponseDto<>(System.currentTimeMillis(), "Appointment created successfully", HttpStatus.OK.value()));
	}
	
}
