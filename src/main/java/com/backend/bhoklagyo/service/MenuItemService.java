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
import com.backend.bhoklagyo.exception.MenuItemNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepo;

    public Page<MenuItemDTO> getMenuByRestaurantFiltered(
            UUID restaurantId,
            String category,
            Double maxPrice,
            Double minPrice,
            Integer preparationTime,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MenuItem> menuPage = menuItemRepository.findFiltered(
                restaurantId,
                category,
                maxPrice,
                minPrice,
                preparationTime,
                pageable
        );

        return menuPage.map(MenuItemMapper::toDTO);
    }

    public Page<MenuItemDTO> getMenu(
            String name,
            Boolean veg,
            String category,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MenuItem> menuPage = menuItemRepository.filterMenuItems(
                name,
                veg,
                category,
                pageable
        );

        return menuPage.map(MenuItemMapper::toDTO);
    }


    public MenuItemDTO getMenuItem(UUID itemId) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        return MenuItemMapper.toDTO(item);
    }

    public MenuItemDTO createMenuItem(UUID restaurantId, CreateMenuItemDTO dto) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem item = MenuItemMapper.toEntity(dto, restaurant);

        return MenuItemMapper.toDTO(menuItemRepository.save(item));
    }

    public MenuItemDTO updateMenuItem(UUID itemId, CreateMenuItemDTO dto) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());
        item.setVeg(dto.getVeg());
        item.setPreparationTimeMins(dto.getPreparationTimeMins());
        item.setImageKey(dto.getImageKey());

        return MenuItemMapper.toDTO(menuItemRepository.save(item));
    }

    public void deleteMenuItem(UUID itemId) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuItemRepository.delete(item);
    }

    public Page<MenuItemDTO> getMenu(String category, Boolean veg, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuItem> menuPage = menuItemRepository.filterMenuItems(
                null, // name filter not used here
                veg,
                category,
                pageable
        );
        return menuPage.map(MenuItemMapper::toDTO);
    }
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll()
                .stream()
                .map(MenuItemMapper::toDTO)
                .toList();
    }

    public MenuItem getMenuItemById(UUID id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));
    }


}
