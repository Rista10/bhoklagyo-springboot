package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.dto.menu.CreateMenuItemDTO;
import com.backend.bhoklagyo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public List<MenuItemDTO> getMenu(@PathVariable Long restaurantId) {
        return menuItemService.getMenu(restaurantId);
    }

    @GetMapping("/{itemId}")
    public MenuItemDTO getMenuItem(@PathVariable Long restaurantId,
                                   @PathVariable Long itemId) {
        return menuItemService.getMenuItem(restaurantId, itemId);
    }

    @PostMapping
    public MenuItemDTO createMenuItem(@PathVariable Long restaurantId,
                                      @RequestBody CreateMenuItemDTO dto) {
        return menuItemService.createMenuItem(restaurantId, dto);
    }

    @PutMapping("/{itemId}")
    public MenuItemDTO updateMenuItem(@PathVariable Long restaurantId,
                                      @PathVariable Long itemId,
                                      @RequestBody CreateMenuItemDTO dto) {
        return menuItemService.updateMenuItem(restaurantId, itemId, dto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteMenuItem(@PathVariable Long restaurantId,
                               @PathVariable Long itemId) {
        menuItemService.deleteMenuItem(restaurantId, itemId);
    }
}
