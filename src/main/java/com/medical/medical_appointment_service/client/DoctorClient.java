package com.medical.medical_appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.medical.medical_appointment_service.dto.ApiResponse;

@FeignClient(name = "medical-doctor-service")
public interface DoctorClient {

	@GetMapping("/doctors/{doctorId}")
    ApiResponse<?> checkDoctor(@PathVariable Long doctorId);

    @GetMapping("/doctors/{doctorId}/availability")
    ApiResponse<Boolean> isDoctorAvailable(
            @PathVariable Long doctorId,
            @RequestParam String date,
            @RequestParam String time
    );
}
