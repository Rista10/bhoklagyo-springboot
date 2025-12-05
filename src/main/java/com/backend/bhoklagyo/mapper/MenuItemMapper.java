package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.dto.menu.CreateMenuItemDTO;
import com.backend.bhoklagyo.model.MenuItem;
import com.backend.bhoklagyo.model.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class MenuItemMapper {

    private MenuItemMapper() {}

    public static MenuItemDTO toDTO(MenuItem entity) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setVeg(entity.getVeg());
        dto.setPreparationTimeMins(entity.getPreparationTimeMins());
        dto.setImageKey(entity.getImageKey());
        dto.setRestaurantId(entity.getRestaurant() != null ? entity.getRestaurant().getId() : null);
        return dto;
    }

    public static List<MenuItemDTO> toDTOs(List<MenuItem> entities) {
        return entities.stream()
                .map(MenuItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static MenuItem toEntity(CreateMenuItemDTO dto, Restaurant restaurant) {
        MenuItem item = new MenuItem();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());
        item.setRestaurant(restaurant);
        item.setVeg(dto.getVeg());
        item.setPreparationTimeMins(dto.getPreparationTimeMins());
        item.setImageKey(dto.getImageKey());
        item.setRestaurant(restaurant);
        return item;
    }
}
