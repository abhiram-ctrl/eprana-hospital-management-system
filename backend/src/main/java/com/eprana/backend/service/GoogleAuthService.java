package com.eprana.backend.service;

import com.eprana.backend.dto.response.LoginResponseDto;
import com.eprana.backend.entity.User;
import com.eprana.backend.enums.Role;
import com.eprana.backend.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GoogleAuthService(
            UserRepository userRepository,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseDto googleLogin(
            String credential
    ) throws Exception {

        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance()
                )
                        .setAudience(
                                Collections.singletonList(
                                        "916416778087-h3jqvrnc3mu746knt7haol9sknn5rg92.apps.googleusercontent.com"
                                )
                        )
                        .build();



        GoogleIdToken idToken =
                verifier.verify(credential);

        if (idToken == null) {
            throw new RuntimeException(
                    "Invalid Google Token"
            );
        }

        GoogleIdToken.Payload payload =
                idToken.getPayload();

      

        String email =
                payload.getEmail();

        String name =
                (String) payload.get("name");

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseGet(() -> {

                            User newUser =
                                    new User();

                            newUser.setName(name);
                            newUser.setEmail(email);
                            newUser.setRole(Role.PATIENT);

                            newUser.setEmailVerified(true);

                            return userRepository.save(
                                    newUser
                            );
                        });

        String jwt =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole()
                );

        return new LoginResponseDto(
                jwt,
                user.getRole().name()
        );
    }
}