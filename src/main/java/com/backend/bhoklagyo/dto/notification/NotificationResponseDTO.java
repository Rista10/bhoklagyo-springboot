package com.backend.bhoklagyo.dto.notification;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {
    private UUID id;
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
}
