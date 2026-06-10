package com.eprana.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "passwors is required")
    private String password;
}
