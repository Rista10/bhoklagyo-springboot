package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.dto.menu.CreateMenuItemDTO;
import com.backend.bhoklagyo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping("/restaurants/{restaurantId}/menu")
    public MenuItemDTO createMenuItem(@PathVariable UUID restaurantId,
                                      @RequestBody CreateMenuItemDTO dto) {
        return menuItemService.createMenuItem(restaurantId, dto);
    }

    @GetMapping("/restaurants/{restaurantId}/menu")
    public Page<MenuItemDTO> getMenu(
        @PathVariable UUID restaurantId,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Integer preparationTime,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return menuItemService.getMenuByRestaurantFiltered(
                restaurantId, category, maxPrice, minPrice, preparationTime, page, size
        );
    }

   
    @GetMapping("/menu/{id}")
    public MenuItemDTO getMenuItem(@PathVariable UUID id) {
        return menuItemService.getMenuItem(id);
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/menu/{id}")
    public MenuItemDTO updateMenuItem(@PathVariable UUID id,
                                      @RequestBody CreateMenuItemDTO dto) {
        return menuItemService.updateMenuItem(id, dto);
    }


    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @DeleteMapping("/menu/{id}")
    public void deleteMenuItem(@PathVariable UUID id) {
        menuItemService.deleteMenuItem(id);
    }

    @GetMapping("/menu")
    public Page<MenuItemDTO> getMenuByCategory(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (category != null && !category.isBlank()) {
            return menuItemService.getMenuByCategory(category, page, size);
        }

        return menuItemService.getAllMenuItemsPaginated(page, size);
    }

    @GetMapping("/menu/search")
    public Page<MenuItemDTO> searchMenuItems(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return menuItemService.searchMenuItemsByName(q, page, size);
    }


}
