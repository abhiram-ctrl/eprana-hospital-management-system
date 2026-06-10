package com.eprana.backend.controller;

import com.eprana.backend.dto.ReminderRequestDto;
import com.eprana.backend.dto.response.MedicationReminderResponseDto;
import com.eprana.backend.service.MedicationReminderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
public class MedicationReminderController {

    private final MedicationReminderService reminderService;

    public MedicationReminderController(
            MedicationReminderService reminderService
    ) {
        this.reminderService = reminderService;
    }

    // PATIENT creates reminders
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @PostMapping
    public MedicationReminderResponseDto createReminder(
            @Valid @RequestBody ReminderRequestDto dto
    ) {

        return reminderService.createReminder(dto);
    }

    // ADMIN + PATIENT view reminders
    @PreAuthorize(
            "hasAnyRole('ADMIN','PATIENT')"
    )
    @GetMapping
    public List<MedicationReminderResponseDto> getAllReminders() {

        return reminderService.getAllReminders();
    }
}