package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.restaurant.RestaurantRequestDTO;
import com.backend.bhoklagyo.dto.restaurant.RestaurantResponseDTO;
import com.backend.bhoklagyo.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO request) {
        RestaurantResponseDTO created = restaurantService.createRestaurant(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody RestaurantRequestDTO request
    ) {
        RestaurantResponseDTO updated = restaurantService.updateRestaurant(restaurantId, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantById(restaurantId) != null
                ? ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }

    // Search & filter
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponseDTO>> searchRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine
    ) {
        List<RestaurantResponseDTO> results = restaurantService.searchRestaurants(name, cuisine);
        return ResponseEntity.ok(results);
    }

    // Nearby (commented out for now)
    // @GetMapping("/nearby")
    // public ResponseEntity<List<RestaurantResponseDTO>> getNearbyRestaurants(
    //         @RequestParam double lat,
    //         @RequestParam double lng,
    //         @RequestParam double radius
    // ) {
    //     List<RestaurantResponseDTO> nearby = restaurantService.getNearby(lat, lng, radius);
    //     return ResponseEntity.ok(nearby);
    // }
}
