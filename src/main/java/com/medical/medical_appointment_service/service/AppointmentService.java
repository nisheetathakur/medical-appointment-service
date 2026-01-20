package com.medical.medical_appointment_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.medical_appointment_service.entity.Appointment;
import com.medical.medical_appointment_service.entity.Status;
import com.medical.medical_appointment_service.exception.ResourceNotFoundException;
import com.medical.medical_appointment_service.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public Appointment bookAppointment(Appointment appointment) {
        appointment.setStatus(Status.BOOKED);
        return repository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Appointment cancelAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Appointment not found with id " + id)
                );

        appointment.setStatus(Status.CANCELLED);
        return repository.save(appointment);
    }

    public Appointment completeAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Appointment not found with id " + id)
                );

        appointment.setStatus(Status.COMPLETED);
        return repository.save(appointment);
    }
}
