package com.eprana.backend.entity;

import com.eprana.backend.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentTime;
@Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    // Many appointments -> one patient
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Many appointments -> one doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}