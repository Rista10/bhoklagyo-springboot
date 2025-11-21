package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.model.Restaurant;
import com.backend.bhoklagyo.dto.RestaurantResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {

    // Single entity → DTO
    public static RestaurantResponseDTO toDTO(Restaurant restaurant) {
        if (restaurant == null) return null;

        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(restaurant.getId());
        dto.setRestaurantName(restaurant.getRestaurantName());
        dto.setDescription(restaurant.getDescription());
        dto.setCuisineType(restaurant.getCuisineType());
        dto.setRating(restaurant.getRating());
        dto.setOwnerId(restaurant.getOwner() != null ? restaurant.getOwner().getId() : null);
        dto.setAddress(restaurant.getAddress());
        dto.setPhoneNumber(restaurant.getPhoneNumber());
        dto.setOpeningTime(restaurant.getOpeningTime());
        dto.setClosingTime(restaurant.getClosingTime());

        return dto;
    }

    // List of entities → List of DTOs
    public static List<RestaurantResponseDTO> toDTOs(List<Restaurant> restaurants) {
        if (restaurants == null) return List.of();
        return restaurants.stream()
                .map(RestaurantMapper::toDTO)
                .collect(Collectors.toList());
    }
}
