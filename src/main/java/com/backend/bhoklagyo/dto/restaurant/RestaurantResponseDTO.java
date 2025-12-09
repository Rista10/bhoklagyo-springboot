package com.backend.bhoklagyo.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private UUID id;
    private String restaurantName;
    private String description;
    private String cuisineType;
    private Double rating;
    private UUID ownerId;
    private String address;
    private String phoneNumber;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String logoKey;
}
