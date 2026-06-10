package com.eprana.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorRequestDto {

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotNull(message = "Experience is required")
    private Integer experience;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotNull(message = "Consultation fee is required")
    private Double consultationFee;

    @NotNull(message = "User ID is required")
    private Long userId;
}