package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.restaurant.RestaurantRequestDTO;
import com.backend.bhoklagyo.dto.restaurant.RestaurantResponseDTO;
import com.backend.bhoklagyo.mapper.RestaurantMapper;
import com.backend.bhoklagyo.model.Owner;
import com.backend.bhoklagyo.model.Restaurant;
import com.backend.bhoklagyo.repository.OwnerRepository;
import com.backend.bhoklagyo.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    public RestaurantService(
        RestaurantRepository restaurantRepository,
        OwnerRepository ownerRepository,
        UserRepository userRepository
    ) {
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }


    // Get all restaurants
    public List<RestaurantResponseDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return RestaurantMapper.toDTOs(restaurants);
    }

    // Get restaurant by ID
    public RestaurantResponseDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id " + id));
        return RestaurantMapper.toDTO(restaurant);
    }

    // Create new restaurant
    public RestaurantResponseDTO createRestaurant(
            RestaurantRequestDTO request,
            Authentication authentication
    ) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String auth0Id = jwt.getSubject();

        // 1. Get User from auth0Id
        User user = userRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new RuntimeException("User not found for auth0Id: " + auth0Id));

        // 2. Get Owner from User
        Owner owner = ownerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User is not a restaurant owner"));

        // 3. Create restaurant
        Restaurant restaurant = RestaurantMapper.toEntity(request);
        restaurant.setOwner(owner);

        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantMapper.toDTO(saved);
    }


    // Update existing restaurant
    public RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantRequestDTO request) {

        Restaurant updated = restaurantRepository.findById(restaurantId)
                .map(restaurant -> {

                    restaurant.setRestaurantName(request.getRestaurantName());
                    restaurant.setDescription(request.getDescription());
                    restaurant.setCuisineType(request.getCuisineType());
                    restaurant.setRating(request.getRating());
                    restaurant.setAddress(request.getAddress());
                    restaurant.setPhoneNumber(request.getPhoneNumber());
                    restaurant.setOpeningTime(request.getOpeningTime());
                    restaurant.setClosingTime(request.getClosingTime());

                    return restaurantRepository.save(restaurant);
                })
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id " + restaurantId));

        return RestaurantMapper.toDTO(updated);
    }


    // Delete restaurant
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    // Search & filter restaurants
    public List<RestaurantResponseDTO> searchRestaurants(String name, String category) {
        List<Restaurant> restaurants;

        if (name != null && category != null) {
            restaurants = restaurantRepository
                    .findByRestaurantNameContainingIgnoreCaseAndCuisineTypeIgnoreCase(name, category);
        } else if (name != null) {
            restaurants = restaurantRepository.findByRestaurantNameContainingIgnoreCase(name);
        } else if (category != null) {
            restaurants = restaurantRepository.findByCuisineTypeIgnoreCase(category);
        } else {
            restaurants = restaurantRepository.findAll();
        }

        return RestaurantMapper.toDTOs(restaurants);
    }
}
