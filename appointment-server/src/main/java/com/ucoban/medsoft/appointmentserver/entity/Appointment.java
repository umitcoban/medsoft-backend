package com.ucoban.medsoft.appointmentserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String doctorId;
	private String patientId;
	private LocalTime time;
	private LocalDate date;
	@Enumerated(EnumType.STRING)
	private EAppointmentStatus status;
}
