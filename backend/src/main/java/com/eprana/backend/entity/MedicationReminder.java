package com.eprana.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "medication_reminders")
public class MedicationReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;

    private LocalDateTime reminderTime;

    private Boolean completed = false;

    // Relationship with Prescription
    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}