package com.eprana.backend.dto.response;

import lombok.Data;

@Data
public class PatientResponseDto {

    private Long id;

    private Integer age;

    private String gender;

    private String bloodGroup;

    private String disease;

    private String patientName;
}