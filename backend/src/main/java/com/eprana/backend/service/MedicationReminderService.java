package com.eprana.backend.service;

import com.eprana.backend.dto.ReminderRequestDto;
import com.eprana.backend.dto.response.MedicationReminderResponseDto;
import com.eprana.backend.entity.MedicationReminder;
import com.eprana.backend.entity.Prescription;
import com.eprana.backend.repository.MedicationReminderRepository;
import com.eprana.backend.repository.PrescriptionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicationReminderService {

    private final MedicationReminderRepository reminderRepository;

    private final PrescriptionRepository prescriptionRepository;

    public MedicationReminderService(
            MedicationReminderRepository reminderRepository,
            PrescriptionRepository prescriptionRepository
    ) {

        this.reminderRepository = reminderRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    private MedicationReminderResponseDto mapToResponse(
            MedicationReminder reminder
    )
    {
        MedicationReminderResponseDto dto =
                new MedicationReminderResponseDto();

        dto.setId(
                reminder.getId()
        );

        dto.setMedicineName(
                reminder.getMedicineName()
        );

        dto.setReminderTime(
                reminder.getReminderTime()
        );

        dto.setCompleted(
                reminder.getCompleted()
        );

        dto.setPatientName(
                reminder.getPrescription()
                        .getAppointment()
                        .getPatient()
                        .getUser()
                        .getName()
        );

        return dto;
    }

    // Create Reminder
    public MedicationReminderResponseDto createReminder(
            ReminderRequestDto dto
    ) {

        Prescription prescription =
                prescriptionRepository.findById(
                                dto.getPrescriptionId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Prescription not found"
                                ));

        MedicationReminder reminder =
                new MedicationReminder();

        reminder.setMedicineName(
                dto.getMedicineName()
        );

        reminder.setReminderTime(
                dto.getReminderTime()
        );

        reminder.setPrescription(
                prescription
        );

        MedicationReminder savedReminder =
                reminderRepository.save(
                        reminder
                );

        return mapToResponse(
                savedReminder
        );
    }

    // Get All Reminders
    public List<MedicationReminderResponseDto>
    getAllReminders()
    {
        return reminderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // AUTOMATIC REMINDER CHECKER
    @Scheduled(fixedRate = 60000)
    public void checkReminders() {

        List<MedicationReminder> reminders =
                reminderRepository
                        .findByReminderTimeBeforeAndCompletedFalse(
                                LocalDateTime.now()
                        );

        for (MedicationReminder reminder : reminders) {

            System.out.println(
                    "=================================="
            );

            System.out.println(
                    "MEDICINE REMINDER"
            );

            System.out.println(
                    "Patient : "
                            + reminder.getPrescription()
                            .getAppointment()
                            .getPatient()
                            .getUser()
                            .getName()
            );

            System.out.println(
                    "Medicine : "
                            + reminder.getMedicineName()
            );

            System.out.println(
                    "Time : "
                            + reminder.getReminderTime()
            );

            System.out.println(
                    "=================================="
            );

            reminder.setCompleted(true);

            reminderRepository.save(reminder);
        }
    }
}