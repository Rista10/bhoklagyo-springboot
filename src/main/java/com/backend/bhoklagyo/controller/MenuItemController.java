package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.dto.menu.CreateMenuItemDTO;
import com.backend.bhoklagyo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import com.backend.bhoklagyo.dto.menu.MenuFilterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PostMapping("/restaurants/{restaurantId}/menu")
    public ResponseEntity<MenuItemDTO> createMenuItem(
            @PathVariable UUID restaurantId,
            @RequestBody CreateMenuItemDTO dto
    ) {
        MenuItemDTO created = menuItemService.createMenuItem(restaurantId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }


    @PostMapping("/restaurants/{restaurantId}/menu/search")
    public ResponseEntity<Page<MenuItemDTO>> getMenu(
            @PathVariable UUID restaurantId,
            @RequestBody MenuFilterRequest request
    ) {
        return ResponseEntity.ok(
                menuItemService.getMenuByRestaurantFiltered(restaurantId, request)
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

    @PostMapping("/menu")
    public ResponseEntity<Page<MenuItemDTO>> getMenu(
            @RequestBody MenuFilterRequest request
    ) {
        Page<MenuItemDTO> menu = menuItemService.getMenu(request);
        return ResponseEntity.ok(menu);
    }

}
