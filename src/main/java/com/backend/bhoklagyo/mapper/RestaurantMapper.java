package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.restaurant.RestaurantRequestDTO;
import com.backend.bhoklagyo.dto.restaurant.RestaurantResponseDTO;
import com.backend.bhoklagyo.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapper {

    private RestaurantMapper() {}

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

    public static Restaurant toEntity(RestaurantRequestDTO dto) {
        if (dto == null) return null;

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setDescription(dto.getDescription());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setRating(dto.getRating());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhoneNumber(dto.getPhoneNumber());
        restaurant.setOpeningTime(dto.getOpeningTime());
        restaurant.setClosingTime(dto.getClosingTime());

        return restaurant;
    }


    public static List<RestaurantResponseDTO> toDTOs(List<Restaurant> restaurants) {
        if (restaurants == null) return List.of();
        return restaurants.stream()
                .map(RestaurantMapper::toDTO)
                .collect(Collectors.toList());
    }
}
