package com.eprana.backend.controller;

import com.eprana.backend.dto.LoginRequestDto;
import com.eprana.backend.dto.UserRequestDto;
import com.eprana.backend.dto.response.LoginResponseDto;
import com.eprana.backend.dto.response.UserResponseDto;
import com.eprana.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //Register
    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRequestDto dto){

        return userService.createUser(dto);
    }

    //Login
    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto dto){
        return userService.login(dto);
    }
}
