package com.eprana.backend.service;

import java.time.LocalDateTime;
import java.util.Random;
import com.eprana.backend.dto.LoginRequestDto;
import com.eprana.backend.dto.UserRequestDto;
import com.eprana.backend.dto.response.LoginResponseDto;
import com.eprana.backend.dto.response.UserResponseDto;
import com.eprana.backend.entity.User;
import com.eprana.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.eprana.backend.dto.OtpVerificationRequestDto;

import java.util.List;
@Service
public class UserService {
    public final UserRepository userRepository;
   private final BCryptPasswordEncoder passwordEncoder;
   private final JwtService jwtService;
    private final EmailService emailService;
    public UserService(UserRepository userRepository,JwtService jwtService,BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
       this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }
    private UserResponseDto mapToResponse(User user) {

        UserResponseDto dto =
                new UserResponseDto();

        dto.setId(user.getId());

        dto.setName(user.getName());

        dto.setEmail(user.getEmail());

        dto.setRole(user.getRole());

        return dto;
    }
    public UserResponseDto createUser(UserRequestDto dto) {
       if(userRepository.existsByEmail(dto.getEmail()))
       {
           throw new RuntimeException("Email already exists");
       }
       User user=new User();
       user.setName(dto.getName());
       user.setEmail(dto.getEmail());
       user.setRole(dto.getRole());
       user.setPassword(passwordEncoder.encode(dto.getPassword()));

        String otp =
                String.valueOf(
                        100000 +
                                new Random().nextInt(900000)
                );

        user.setOtp(otp);

        user.setOtpExpiry(
                LocalDateTime.now()
                        .plusMinutes(10)
        );

        user.setEmailVerified(false);

        User savedUser=userRepository.save(user);

        emailService.sendEmail(
                user.getEmail(),
                "E-Prana OTP Verification",
                "Your OTP is: " + otp
        );

        return mapToResponse(savedUser);
    }
    public List<UserResponseDto> getAllUsers()
    {
        return userRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public LoginResponseDto login(LoginRequestDto dto)
    {
        User user=userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or pasword"));
        boolean passwordMatches=
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );
        if(!passwordMatches)
        {
            throw new RuntimeException("Invalid password");
        }

        if(!user.isEmailVerified())
        {
            throw new RuntimeException(
                    "Please verify your email first"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        return new LoginResponseDto(
                token,
                user.getRole().name()
        );
    }

    public String verifyOtp(
            OtpVerificationRequestDto request
    ) {

        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new RuntimeException("User not found")
                        );

        if(user.isEmailVerified()) {
            return "Email already verified";
        }

        if(user.getOtp() == null) {
            throw new RuntimeException("OTP not found");
        }

        if(!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if(user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        user.setEmailVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "Email verified successfully";
    }

}
