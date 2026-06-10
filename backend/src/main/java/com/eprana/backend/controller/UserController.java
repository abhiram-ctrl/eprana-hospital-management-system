package com.eprana.backend.controller;

import com.eprana.backend.dto.UserRequestDto;
import com.eprana.backend.dto.response.UserResponseDto;
import com.eprana.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService= userService;
    }
    @PostMapping
    public UserResponseDto createUser(@Valid@RequestBody UserRequestDto dto) {
        return userService.createUser(dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDto>getAllUsers(){
        return userService.getAllUsers();
    }
}
