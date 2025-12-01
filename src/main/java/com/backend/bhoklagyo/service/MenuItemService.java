package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.dto.menu.CreateMenuItemDTO;
import com.backend.bhoklagyo.mapper.MenuItemMapper;
import com.backend.bhoklagyo.model.MenuItem;
import com.backend.bhoklagyo.model.Restaurant;
import com.backend.bhoklagyo.repository.MenuItemRepository;
import com.backend.bhoklagyo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepo;
    private final RestaurantRepository restaurantRepo;

    public List<MenuItemDTO> getMenu(Long restaurantId) {
        return menuItemRepo.findByRestaurantId(restaurantId)
                .stream()
                .map(MenuItemMapper::toDTO)
                .toList();
    }

    public MenuItemDTO getMenuItem(Long restaurantId, Long itemId) {
        MenuItem item = menuItemRepo.findByIdAndRestaurantId(itemId, restaurantId);
        if (item == null) throw new RuntimeException("Menu item not found");
        return MenuItemMapper.toDTO(item);
    }

    public MenuItemDTO createMenuItem(Long restaurantId, CreateMenuItemDTO dto) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem item = MenuItemMapper.toEntity(dto, restaurant);
        return MenuItemMapper.toDTO(menuItemRepo.save(item));
    }

    public MenuItemDTO updateMenuItem(Long restaurantId, Long itemId, CreateMenuItemDTO dto) {
        MenuItem item = menuItemRepo.findByIdAndRestaurantId(itemId, restaurantId);
        if (item == null) throw new RuntimeException("Menu item not found");

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());

        return MenuItemMapper.toDTO(menuItemRepo.save(item));
    }

    public void deleteMenuItem(Long restaurantId, Long itemId) {
        MenuItem item = menuItemRepo.findByIdAndRestaurantId(itemId, restaurantId);
        if (item == null) throw new RuntimeException("Menu item not found");
        menuItemRepo.delete(item);
    }
}
