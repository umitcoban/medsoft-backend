package com.ucoban.medsoft.appointmentserver.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentCreateDto(String doctorId, String patientId, LocalDate date, LocalTime time) {
}
