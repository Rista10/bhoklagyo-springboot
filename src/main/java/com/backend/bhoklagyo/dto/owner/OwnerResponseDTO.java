package com.backend.bhoklagyo.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OwnerResponseDTO {
    private Long id;
    private String panNumber;
    private String bankAccountNumber;
    private String bankName;
    private LocalDateTime createdAt;
    private Long userId; 
}
