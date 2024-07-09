package com.ucoban.medsoft.appointmentserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentDto {
	Long id;
	AccountDto doctor;
	AccountDto patient;
	LocalDate date;
	LocalTime time;
	String status;
}
