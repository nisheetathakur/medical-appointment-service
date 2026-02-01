package com.medical.medical_appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medical.medical_appointment_service.dto.ApiResponse;

@FeignClient(name = "medical-patient-service")
public interface PatientClient {

    @GetMapping("/patients/profile/patient/{patientId}")
    ApiResponse<?> checkPatient(@PathVariable Long patientId);
}
