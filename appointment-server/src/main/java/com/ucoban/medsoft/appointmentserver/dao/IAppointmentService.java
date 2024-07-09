package com.ucoban.medsoft.appointmentserver.dao;

import com.ucoban.medsoft.appointmentserver.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAppointmentService {
	void addAppointment(Appointment appointment);
	List<Appointment> getAppointments();
	Appointment getAppointment(long id);
	List<Appointment> findByDoctorId(String id);
	List<Appointment> findByPatientId(String id);
	void deleteAppointment(long id);
}
