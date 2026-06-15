package com.eprana.backend.controller;

import com.eprana.backend.dto.LoginRequestDto;
import com.eprana.backend.dto.UserRequestDto;
import com.eprana.backend.dto.response.LoginResponseDto;
import com.eprana.backend.dto.response.UserResponseDto;
import com.eprana.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eprana.backend.dto.OtpVerificationRequestDto;
import com.eprana.backend.dto.GoogleLoginRequestDto;
import com.eprana.backend.service.GoogleAuthService;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final GoogleAuthService googleAuthService;
    public AuthController(UserService userService, GoogleAuthService googleAuthService) {
        this.userService = userService;
        this.googleAuthService=googleAuthService;
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

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestBody
            OtpVerificationRequestDto request
    ) {

        return ResponseEntity.ok(
                userService.verifyOtp(request)
        );
    }
   //endpoint for oauth google login
    @PostMapping("/google")
    public LoginResponseDto googleLogin(
            @RequestBody
            GoogleLoginRequestDto request
    ) throws Exception {

        return googleAuthService.googleLogin(
                request.getCredential()
        );
    }


}
