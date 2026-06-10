package com.eprana.backend.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicationReminderResponseDto {

    private Long id;

    private String medicineName;

    private LocalDateTime reminderTime;

    private Boolean completed;

    private String patientName;
}