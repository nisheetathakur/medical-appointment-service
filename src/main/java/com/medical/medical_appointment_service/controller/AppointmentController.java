package com.medical.medical_appointment_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.medical.medical_appointment_service.dto.ApiResponse;
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
    public ApiResponse<Appointment> book(
            @Valid @RequestBody Appointment appointment) {

        Appointment saved = service.bookAppointment(appointment);

        return ApiResponse.success(
                "Appointment booked successfully",
                saved
        );
    }

    // ---------------------------------
    // GET APPOINTMENTS BY PATIENT
    // ---------------------------------
    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<Appointment>> getByPatient(
            @PathVariable Long patientId) {

        return ApiResponse.success(
                "Appointments fetched successfully",
                service.getAppointmentsByPatient(patientId)
        );
    }

    // ---------------------------------
    // CANCEL APPOINTMENT
    // ---------------------------------
    @PutMapping("/{id}/cancel")
    public ApiResponse<Appointment> cancel(@PathVariable Long id) {

        return ApiResponse.success(
                "Appointment cancelled successfully",
                service.cancelAppointment(id)
        );
    }

    // ---------------------------------
    // COMPLETE APPOINTMENT
    // ---------------------------------
    @PutMapping("/{id}/complete")
    public ApiResponse<Appointment> complete(@PathVariable Long id) {

        return ApiResponse.success(
                "Appointment completed successfully",
                service.completeAppointment(id)
        );
    }

    // ---------------------------------
    // GET ALL APPOINTMENTS
    // ---------------------------------
    @GetMapping
    public ApiResponse<List<Appointment>> getAll() {

        return ApiResponse.success(
                "All appointments fetched successfully",
                service.getAllAppointments()
        );
    }

    // ---------------------------------
    // GET APPOINTMENTS BY DOCTOR
    // ---------------------------------
    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<Appointment>> getByDoctor(
            @PathVariable Long doctorId) {

        return ApiResponse.success(
                "Doctor appointments fetched successfully",
                service.getAppointmentsByDoctor(doctorId)
        );
    }
}
