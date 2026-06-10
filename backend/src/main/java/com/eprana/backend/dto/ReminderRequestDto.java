package com.eprana.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderRequestDto {

    @NotBlank(message = "Medicine name is required")
    private String medicineName;

    @NotNull(message = "Reminder time is required")
    private LocalDateTime reminderTime;

    @NotNull(message = "Prescription ID is required")
    private Long prescriptionId;
}