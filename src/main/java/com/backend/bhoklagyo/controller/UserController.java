package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import com.backend.bhoklagyo.service.UserService;
import com.backend.bhoklagyo.dto.user.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<User> getMe(Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal();

        User user = userService.findOrCreateOrUpdateUser(jwt);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMe(
            @RequestBody UpdateProfileRequest req,
            Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal();
        User user = userService.findOrCreateOrUpdateUser(jwt);

        user.setName(req.getName());
        user.setPhoneNumber(req.getPhoneNumber());
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
