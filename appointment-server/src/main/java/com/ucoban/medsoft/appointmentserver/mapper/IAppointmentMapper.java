package com.ucoban.medsoft.appointmentserver.mapper;

import com.ucoban.medsoft.appointmentserver.dao.client.IAccountFeignClient;
import com.ucoban.medsoft.appointmentserver.dto.AccountDto;
import com.ucoban.medsoft.appointmentserver.dto.AppointmentCreateDto;
import com.ucoban.medsoft.appointmentserver.dto.AppointmentDto;
import com.ucoban.medsoft.appointmentserver.entity.Appointment;
import org.mapstruct.*;

import java.util.List;


@Mapper(implementationName = "AppointmentMapperImpl", componentModel = "spring")
public interface IAppointmentMapper {
	
	@Mapping(target = "doctor", source = "doctorId", qualifiedByName = "mapDoctorIdToAccount")
	@Mapping(target = "patient", source = "patientId", qualifiedByName = "mapPatientIdToAccount")
	AppointmentDto toAppointmentDto(Appointment appointment, @Context IAccountFeignClient accountFeignClient);
	
	List<AppointmentDto> toAppointmentDtoList(List<Appointment> appointments, @Context IAccountFeignClient accountFeignClient);
	
	Appointment toAppointment(AppointmentCreateDto appointmentCreateDto);
	
	@Named("mapDoctorIdToAccount")
	default AccountDto mapDoctorIdToAccount(String doctorId, @Context IAccountFeignClient accountFeignClient) {
		return accountFeignClient.getAccountById(doctorId).getBody().data();
	}
	
	@Named("mapPatientIdToAccount")
	default AccountDto mapPatientIdToAccount(String patientId, @Context IAccountFeignClient accountFeignClient) {
		return accountFeignClient.getAccountById(patientId).getBody().data();
	}
}