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
import com.backend.bhoklagyo.mapper.MenuItemMapper;
import com.backend.bhoklagyo.model.MenuItem;
import com.backend.bhoklagyo.repository.MenuItemRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import com.backend.bhoklagyo.exception.NoSuchRestaurantExistsException;
import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.service.AuthService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final AuthService authService;


    // Get all restaurants
    public List<RestaurantResponseDTO> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Restaurant> restaurants = restaurantRepository.findAll(pageable).getContent();
        return RestaurantMapper.toDTOs(restaurants);
    }

    // Get restaurant by ID
    public RestaurantResponseDTO getRestaurantById(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id " + id));
        return RestaurantMapper.toDTO(restaurant);
    }

    // Create new restaurant
    public RestaurantResponseDTO createRestaurant(
            RestaurantRequestDTO request,
            Authentication authentication
    ) {
        User user = authService.getCurrentUser(authentication);
        Owner owner = ownerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User is not a restaurant owner"));

        Restaurant restaurant = RestaurantMapper.toEntity(request);
        restaurant.setOwner(owner);

        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantMapper.toDTO(saved);
    }


    public RestaurantResponseDTO updateRestaurant(UUID restaurantId, RestaurantRequestDTO request) {

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
    public void deleteRestaurant(UUID restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }


    public List<RestaurantResponseDTO> getNearbyRestaurants(Double lat, Double lng, Double radius) {
        List<Restaurant> restaurants = restaurantRepository.findNearby(lat, lng, radius);
        return RestaurantMapper.toDTOs(restaurants);
    }

    public Page<MenuItemDTO> getFilteredMenu(
            UUID restaurantId,
            String category,
            Double price,
            Integer preparationTime,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MenuItem> menuPage = menuItemRepository.findFiltered(
                restaurantId,
                category,
                price,
                preparationTime,
                pageable
        );

        return menuPage.map(MenuItemMapper::toDTO);
    }

        public Restaurant getRestaurant(UUID id) {
                return restaurantRepository.findById(id)
                    .orElseThrow(() -> new NoSuchRestaurantExistsException("Restaurant not found with id " + id));
            }
   
   public List<RestaurantResponseDTO> getRestaurantsByOwner(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
                
        Owner owner = ownerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Owner not found for user with id " + userId));

        List<Restaurant> restaurants = restaurantRepository.findByOwner(owner);
        return RestaurantMapper.toDTOs(restaurants);
    }

}
