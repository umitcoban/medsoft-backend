package com.ucoban.medsoft.appointmentserver.repository;

import com.ucoban.medsoft.appointmentserver.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByPatientId(String patientId);
	List<Appointment> findByDoctorId(String doctorId);
	List<Appointment> findByPatientIdAndDoctorId(String patientId, String doctorId);
}
