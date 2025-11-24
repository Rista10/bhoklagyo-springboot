package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.menu.MenuItemDTO;
import com.backend.bhoklagyo.dto.menu.CreateMenuItemDTO;
import com.backend.bhoklagyo.model.MenuItem;
import com.backend.bhoklagyo.model.Restaurant;

public class MenuItemMapper {

    private MenuItemMapper() {} 

    public static MenuItemDTO toDTO(MenuItem entity) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setAvailable(entity.isAvailable());
        return dto;
    }

    public static MenuItem toEntity(CreateMenuItemDTO dto, Restaurant restaurant) {
        MenuItem item = new MenuItem();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());
        item.setAvailable(dto.isAvailable());
        item.setRestaurant(restaurant);
        return item;
    }
}
