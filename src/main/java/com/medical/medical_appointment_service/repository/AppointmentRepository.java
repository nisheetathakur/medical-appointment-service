package com.medical.medical_appointment_service.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.medical_appointment_service.entity.Appointment;

public interface AppointmentRepository 
extends JpaRepository<Appointment, Long> {

List<Appointment> findByPatientId(Long patientId);

List<Appointment> findByDoctorId(Long doctorId);

boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
    Long doctorId,
    LocalDate appointmentDate,
    LocalTime appointmentTime
);
}
