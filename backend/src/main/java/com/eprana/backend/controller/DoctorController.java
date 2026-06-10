package com.eprana.backend.controller;

import com.eprana.backend.dto.DoctorRequestDto;
import com.eprana.backend.dto.response.DoctorResponseDto;
import com.eprana.backend.entity.Doctor;
import com.eprana.backend.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(
            DoctorService doctorService
    ) {
        this.doctorService = doctorService;
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DoctorResponseDto createDoctor(
            @Valid @RequestBody DoctorRequestDto dto
    ) {

        return doctorService.createDoctor(dto);
    }

    // All authenticated users
    @GetMapping
    public List<DoctorResponseDto> getAllDoctors() {

        return doctorService.getAllDoctors();
    }

    // Search doctors by specialization
    @GetMapping("/search")
    public List<Doctor> searchDoctors(
            @RequestParam String specialization
    ) {

        return doctorService
                .searchDoctors(specialization);
    }
}