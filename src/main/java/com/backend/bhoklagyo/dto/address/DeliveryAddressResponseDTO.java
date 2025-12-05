package com.backend.bhoklagyo.dto.address;

import lombok.Data;
import java.util.UUID;

@Data
public class DeliveryAddressResponseDTO {

    private UUID id;
    private UUID orderId;

    private String municipality;
    private Integer wardNumber;
    private String toleName;
    private String landmark;
    private Double latitude;
    private Double longitude;
}
