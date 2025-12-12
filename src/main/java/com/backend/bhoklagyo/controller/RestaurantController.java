package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.restaurant.RestaurantRequestDTO;
import com.backend.bhoklagyo.dto.restaurant.RestaurantResponseDTO;
import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.service.RestaurantService;
import com.backend.bhoklagyo.service.MenuItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.service.OrderService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
@CrossOrigin(origins = "http://localhost:5173")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersForRestaurant(
            @PathVariable UUID restaurantId, Authentication authentication) {

        List<OrderDTO> orders = orderService.getOrdersByRestaurant(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByLocation(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false, name = "long") Double lng,
            @RequestParam(required = false) Double radius,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        System.out.println("Fetching restaurants with params - lat: " + lat + ", lng: " + lng + ", radius: " + radius);
        if (lat == null || lng == null || radius == null) {
            return ResponseEntity.ok(restaurantService.getAllRestaurants(page, size));
        }

        return ResponseEntity.ok(
                restaurantService.getNearbyRestaurants(lat, lng, radius)
        );
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(
            @PathVariable UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }


    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(
            @RequestBody RestaurantRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                restaurantService.createRestaurant(request, authentication)
        );
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable UUID restaurantId,
            @RequestBody RestaurantRequestDTO request
    ) {
        return ResponseEntity.ok(
                restaurantService.updateRestaurant(restaurantId, request)
        );
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @GetMapping("/owner/{userId}")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByOwner(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(restaurantService.getRestaurantsByOwner(userId));
    }
}
