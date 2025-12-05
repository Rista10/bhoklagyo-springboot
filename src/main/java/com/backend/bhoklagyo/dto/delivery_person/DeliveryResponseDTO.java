package com.backend.bhoklagyo.dto.delivery_person;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeliveryResponseDTO {
    private UUID id;
    private String licenseNumber;
    private String vehicleNumber;
    private LocalDateTime createdAt;
    private UUID userId;
}
