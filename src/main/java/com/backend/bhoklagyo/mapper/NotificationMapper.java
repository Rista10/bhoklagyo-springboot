package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.notification.NotificationResponseDTO;
import com.backend.bhoklagyo.model.Notification;

public class NotificationMapper {

    public static NotificationResponseDTO toDTO(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
