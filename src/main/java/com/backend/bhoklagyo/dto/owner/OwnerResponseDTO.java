package com.backend.bhoklagyo.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OwnerResponseDTO {
    private UUID id;
    private String panNumber;
    private String bankAccountNumber;
    private String bankName;
    private LocalDateTime createdAt;
    private UUID userId; 
}
