package com.medical.medical_appointment_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.medical_appointment_service.client.DoctorClient;
import com.medical.medical_appointment_service.client.PatientClient;
import com.medical.medical_appointment_service.dto.ApiResponse;
import com.medical.medical_appointment_service.entity.Appointment;
import com.medical.medical_appointment_service.entity.Status;
import com.medical.medical_appointment_service.exception.ResourceNotFoundException;
import com.medical.medical_appointment_service.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private DoctorClient doctorClient;   // 🔥 availability ke liye REQUIRED

    @Autowired
    private AppointmentRepository repository;

    // ---------------------------------
    // BOOK APPOINTMENT (WITH AVAILABILITY)
    // ---------------------------------
    public Appointment bookAppointment(Appointment appointment) {

        // 1️⃣ Patient exist check
        patientClient.checkPatient(appointment.getPatientId());

        // 2️⃣ Doctor exist check
        doctorClient.checkDoctor(appointment.getDoctorId());

        // 3️⃣ Doctor availability check
        ApiResponse<Boolean> availabilityResponse =
                doctorClient.isDoctorAvailable(
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate().toString(),
                        appointment.getAppointmentTime().toString()
                );

        if (availabilityResponse == null || !Boolean.TRUE.equals(availabilityResponse.getData())) {
            throw new IllegalArgumentException(
                    "Doctor not available at selected date and time"
            );
        }

        // 4️⃣ Slot clash check (extra safety)
        boolean alreadyBooked =
                repository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime()
                );

        if (alreadyBooked) {
            throw new IllegalArgumentException(
                    "Doctor already booked for this slot"
            );
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
