package com.eprana.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialization;

    private Integer experience;

    private String qualification;

    private Double consultationFee;

    // Relationship with User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}