package com.backend.bhoklagyo.dto.menu;

import lombok.Data;

@Data
public class MenuItemDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private boolean isAvailable;
}
