package com.backend.bhoklagyo.dto.address;

import lombok.Data;

@Data
public class DeliveryAddressResponseDTO {

    private Long id;
    private Long orderId;

    private String municipality;
    private Integer wardNumber;
    private String toleName;
    private String landmark;
    private Double latitude;
    private Double longitude;
}
