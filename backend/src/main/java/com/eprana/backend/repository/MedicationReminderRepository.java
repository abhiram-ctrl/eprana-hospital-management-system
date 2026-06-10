package com.eprana.backend.repository;

import com.eprana.backend.entity.MedicationReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicationReminderRepository
        extends JpaRepository<MedicationReminder, Long> {

    List<MedicationReminder>
    findByReminderTimeBeforeAndCompletedFalse(
            LocalDateTime time
    );
}