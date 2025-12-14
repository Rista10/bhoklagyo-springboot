package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.notification.NotificationResponseDTO;
import com.backend.bhoklagyo.mapper.NotificationMapper;
import com.backend.bhoklagyo.repository.NotificationRepository;
import com.backend.bhoklagyo.service.AuthService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final AuthService authService;

    public NotificationController(NotificationRepository notificationRepository,
                                  AuthService authService) {
        this.notificationRepository = notificationRepository;
        this.authService = authService;
    }

    @GetMapping("/notifications")
    public List<NotificationResponseDTO> getUserNotifications(Authentication authentication) {

        String userId = authService.getCurrentUserId(authentication);
        UUID userUUID = UUID.fromString(userId);

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userUUID)
                .stream()
                .map(NotificationMapper::toDTO)
                .toList();
    }
}
