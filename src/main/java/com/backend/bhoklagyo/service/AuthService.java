package com.backend.bhoklagyo.service;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User getCurrentUser(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String auth0Id = jwt.getSubject();

        return userRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new RuntimeException("User not found for auth0Id: " + auth0Id));
    }
}
