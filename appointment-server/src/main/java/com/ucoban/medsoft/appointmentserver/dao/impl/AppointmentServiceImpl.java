package com.ucoban.medsoft.appointmentserver.dao.impl;

import com.ucoban.medsoft.appointmentserver.dao.IAppointmentService;
import com.ucoban.medsoft.appointmentserver.entity.Appointment;
import com.ucoban.medsoft.appointmentserver.repository.IAppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

@AllArgsConstructor
@Component
public class AppointmentServiceImpl implements IAppointmentService {
	
	private final IAppointmentRepository appointmentRepository;
	
	@Override
	public void addAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);
	}
	
	@Override
	public List<Appointment> getAppointments() {
		return appointmentRepository.findAll();
	}
	
	@Override
	public Appointment getAppointment(long id) {
		return appointmentRepository.findById(id).orElse(null);
	}
	
	@Override
	public void deleteAppointment(long id) {
		appointmentRepository.deleteById(id);
	}
	
	@Override
	public List<Appointment> findByDoctorId(String id) {
		return appointmentRepository.findByDoctorId(id);
	}
	
	@Override
	public List<Appointment> findByPatientId(String id) {
		return appointmentRepository.findByPatientId(id);
	}
}
