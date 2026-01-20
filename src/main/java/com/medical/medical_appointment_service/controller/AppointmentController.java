package com.medical.medical_appointment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.medical.medical_appointment_service.entity.Appointment;
import com.medical.medical_appointment_service.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;
    
    @PostMapping
    public Appointment book(@Valid @RequestBody Appointment appointment) {
        return service.bookAppointment(appointment);
    }


    // Get appointments for a patient
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return service.getAppointmentsByPatient(patientId);
    }

    // Cancel appointment
    @PutMapping("/{id}/cancel")
    public Appointment cancel(@PathVariable Long id) {
        return service.cancelAppointment(id);
    }

    // Complete appointment
    @PutMapping("/{id}/complete")
    public Appointment complete(@PathVariable Long id) {
        return service.completeAppointment(id);
    }

    // Get all appointments
    @GetMapping
    public List<Appointment> getAll() {
        return service.getAllAppointments();
    }
}
