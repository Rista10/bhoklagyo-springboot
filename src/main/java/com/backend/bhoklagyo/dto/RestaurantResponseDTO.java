package com.backend.bhoklagyo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long id;
    private String restaurantName;
    private String description;
    private String cuisineType;
    private Double rating;
    private Long ownerId;
    private String address;
    private String phoneNumber;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
