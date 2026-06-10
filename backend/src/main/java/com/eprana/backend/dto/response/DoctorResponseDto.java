package com.eprana.backend.dto.response;

import lombok.Data;

@Data
public class DoctorResponseDto {

    private Long id;

    private String specialization;

    private Integer experience;

    private String qualification;

    private Double consultationFee;

    private String doctorName;
}