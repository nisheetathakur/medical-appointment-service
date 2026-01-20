package com.medical.medical_appointment_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.medical_appointment_service.entity.Appointment;

public interface AppointmentRepository 
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

}
