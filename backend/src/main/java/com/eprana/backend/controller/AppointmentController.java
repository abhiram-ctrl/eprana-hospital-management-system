package com.eprana.backend.controller;

import com.eprana.backend.dto.AppointmentRequestDto;
import com.eprana.backend.dto.response.AppointmentResponseDto;
import com.eprana.backend.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService
    ) {
        this.appointmentService = appointmentService;
    }

    // PATIENT can book appointment
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @PostMapping
    public AppointmentResponseDto createAppointment(
            @Valid @RequestBody AppointmentRequestDto dto
    ) {

        return appointmentService.createAppointment(dto);
    }

    // ADMIN + DOCTOR can view all appointments
    @PreAuthorize(
            "hasAnyRole('ADMIN','DOCTOR','PATIENT')"
    )
    @GetMapping
    public List<AppointmentResponseDto> getAllAppointments() {

        return appointmentService.getAllAppointments();
    }
}