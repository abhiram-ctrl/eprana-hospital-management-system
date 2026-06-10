package com.eprana.backend.entity;

import com.eprana.backend.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
@Enumerated(EnumType.STRING)
    private Role role;
    private String password;
}
