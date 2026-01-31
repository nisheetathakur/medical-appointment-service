package com.medical.medical_appointment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.medical.medical_appointment_service.entity.Appointment;
import com.medical.medical_appointment_service.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    // ---------------------------------
    // BOOK APPOINTMENT (ONLY RECEPTIONIST)
    // ---------------------------------
    @PostMapping
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public Appointment book(@Valid @RequestBody Appointment appointment) {
        return service.bookAppointment(appointment);
    }

    // ---------------------------------
    // GET APPOINTMENTS BY PATIENT
    // ---------------------------------
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return service.getAppointmentsByPatient(patientId);
    }

    // ---------------------------------
    // CANCEL APPOINTMENT
    // ---------------------------------
    @PutMapping("/{id}/cancel")
    public Appointment cancel(@PathVariable Long id) {
        return service.cancelAppointment(id);
    }

    // ---------------------------------
    // COMPLETE APPOINTMENT
    // ---------------------------------
    @PutMapping("/{id}/complete")
    public Appointment complete(@PathVariable Long id) {
        return service.completeAppointment(id);
    }

    // ---------------------------------
    // GET ALL APPOINTMENTS
    // ---------------------------------
    @GetMapping
    public List<Appointment> getAll() {
        return service.getAllAppointments();
    }

    // ---------------------------------
    // GET APPOINTMENTS BY DOCTOR
    // ---------------------------------
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        return service.getAppointmentsByDoctor(doctorId);
    }
}
