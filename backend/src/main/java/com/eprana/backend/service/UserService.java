package com.eprana.backend.service;

import com.eprana.backend.dto.LoginRequestDto;
import com.eprana.backend.dto.UserRequestDto;
import com.eprana.backend.dto.response.LoginResponseDto;
import com.eprana.backend.dto.response.UserResponseDto;
import com.eprana.backend.entity.User;
import com.eprana.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    public final UserRepository userRepository;
   private final BCryptPasswordEncoder passwordEncoder;
   private final JwtService jwtService;
    public UserService(UserRepository userRepository,JwtService jwtService,BCryptPasswordEncoder passwordEncoder) {
       this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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
        User savedUser=userRepository.save(user);
        return mapToResponse(savedUser);
    }
    public List<UserResponseDto> getAllUsers()
    {
        return userRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    public LoginResponseDto login(LoginRequestDto dto)
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
}
