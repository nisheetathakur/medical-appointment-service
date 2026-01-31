package com.medical.medical_appointment_service.service;

import java.util.List;
import java.util.Map;

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

    // ---------------------------------
    // BOOK APPOINTMENT (SECURED)
    // ---------------------------------
    public Appointment bookAppointment(Appointment appointment) {

        boolean alreadyBooked =
                repository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime()
                );

        if (alreadyBooked) {
            throw new RuntimeException("Doctor already has an appointment at this time");
        }

        appointment.setStatus(Status.BOOKED);
        return repository.save(appointment);
    }

    // ---------------------------------
    // GET APPOINTMENTS BY PATIENT
    // ---------------------------------
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    // ---------------------------------
    // GET ALL APPOINTMENTS
    // ---------------------------------
    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    // ---------------------------------
    // CANCEL APPOINTMENT
    // ---------------------------------
    public Appointment cancelAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Appointment not found with id " + id)
                );

        appointment.setStatus(Status.CANCELLED);
        return repository.save(appointment);
    }

    // ---------------------------------
    // COMPLETE APPOINTMENT
    // ---------------------------------
    public Appointment completeAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Appointment not found with id " + id)
                );

        appointment.setStatus(Status.COMPLETED);
        return repository.save(appointment);
    }

    // ---------------------------------
    // GET APPOINTMENTS BY DOCTOR
    // ---------------------------------
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return repository.findByDoctorId(doctorId);
    }
}
