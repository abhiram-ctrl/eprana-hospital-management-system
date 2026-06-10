package com.eprana.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    private String gender;

    private String bloodGroup;

    private String disease;

    // Relationship with User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}