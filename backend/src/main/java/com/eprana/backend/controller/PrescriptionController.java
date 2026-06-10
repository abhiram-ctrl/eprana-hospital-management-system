package com.eprana.backend.controller;

import com.eprana.backend.dto.PrescriptionRequestDto;
import com.eprana.backend.entity.Prescription;
import com.eprana.backend.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(
            PrescriptionService prescriptionService
    ) {
        this.prescriptionService = prescriptionService;
    }

    // Only DOCTOR can create prescriptions
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public Prescription createPrescription(
            @Valid @RequestBody PrescriptionRequestDto dto
    ) {

        return prescriptionService.createPrescription(dto);
    }

    // ADMIN + DOCTOR can view prescriptions
    @PreAuthorize(
            "hasAnyRole('ADMIN','DOCTOR')"
    )
    @GetMapping
    public List<Prescription> getAllPrescriptions() {

        return prescriptionService.getAllPrescriptions();
    }
}