package com.eprana.backend.controller;

import com.eprana.backend.dto.PatientRequestDto;
import com.eprana.backend.dto.response.PatientResponseDto;
import com.eprana.backend.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(
            PatientService patientService
    ) {
        this.patientService = patientService;
    }

    // ADMIN + DOCTOR can create patient
    @PreAuthorize(
            "hasAnyRole('ADMIN','DOCTOR')"
    )
    @PostMapping
    public PatientResponseDto createPatient(
            @Valid @RequestBody PatientRequestDto dto
    ) {

        return patientService.createPatient(dto);
    }

    // ADMIN + DOCTOR can view patients
    @PreAuthorize(
            "hasAnyRole('ADMIN','DOCTOR')"
    )
    @GetMapping
    public List<PatientResponseDto> getAllPatients() {

        return patientService.getAllPatients();
    }
    // Paginated Patients API
    @PreAuthorize(
            "hasAnyRole('ADMIN','DOCTOR')"
    )
    @GetMapping("/paginated")
    public Page<PatientResponseDto> getPatientsPaginated(
            Pageable pageable
    ) {

        return patientService
                .getPatientsPaginated(pageable);
    }
    // Search patients by disease
    @GetMapping("/search")
    public List<PatientResponseDto> searchPatients(
            @RequestParam String disease
    ) {

        return patientService
                .searchPatients(disease);
    }
}