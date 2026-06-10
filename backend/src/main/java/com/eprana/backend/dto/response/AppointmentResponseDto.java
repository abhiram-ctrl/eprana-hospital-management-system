package com.eprana.backend.dto.response;

import com.eprana.backend.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {

    private Long id;

    private LocalDateTime appointmentTime;

    private AppointmentStatus status;

    private String patientName;

    private String doctorName;
}