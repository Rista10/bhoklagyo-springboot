package com.backend.bhoklagyo.dto.owner;
import lombok.Data;

@Data
public class OwnerRequestDTO {
    private String panNumber;
    private String bankAccountNumber;
    private String bankName;
}
