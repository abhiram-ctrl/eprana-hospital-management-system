package com.eprana.backend.dto.response;

import com.eprana.backend.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private Role role;
}