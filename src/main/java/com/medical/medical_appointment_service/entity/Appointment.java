package com.medical.medical_appointment_service.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @Column(name = "appointment_date", nullable = false)
    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in past")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
