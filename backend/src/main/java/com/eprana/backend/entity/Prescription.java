package com.eprana.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;

    private String dosage;

    private String instructions;

    // One prescription -> one appointment
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}