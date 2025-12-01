package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOrCreateOrUpdateUser(Jwt jwt) {
        String auth0Id = jwt.getSubject();  
        String email = jwt.getClaim("https://bhoklagyo.app/email");
        String name = jwt.getClaim("name"); 

        // Get roles from JWT
        List<String> roles = jwt.getClaimAsStringList("https://bhoklagyo.app/roles");

        return userRepository.findByAuth0Id(auth0Id)
                .map(u -> {
                    u.setEmail(email);
                    u.setName(name);

                    // Sync role from JWT
                    if (roles != null && roles.contains("RESTAURANT_OWNER")) {
                        u.setRole(com.backend.bhoklagyo.enums.Role.RESTAURANT_OWNER);
                    } else if (roles != null && roles.contains("DELIVERY PERSON")) {
                        u.setRole(com.backend.bhoklagyo.enums.Role.DELIVERY_PERSON);
                    } else if (roles != null && roles.contains("CUSTOMER")) {
                        u.setRole(com.backend.bhoklagyo.enums.Role.CUSTOMER);
                    }
                    return userRepository.save(u);
                })
                .orElseGet(() -> {
                    // Create new user
                    User u = new User();
                    u.setAuth0Id(auth0Id);
                    u.setEmail(email);
                    u.setName(name);
                    u.setRole(com.backend.bhoklagyo.enums.Role.CUSTOMER);
                    return userRepository.save(u);
                });
    }
}

