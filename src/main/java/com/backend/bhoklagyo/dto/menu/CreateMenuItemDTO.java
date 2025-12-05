package com.backend.bhoklagyo.dto.menu;

import lombok.Data;

@Data
public class CreateMenuItemDTO {

    private String name;
    private String description;
    private Double price;
    private String category;
    private Integer preparationTimeMins;
    private Boolean veg;
    private String imageKey;
}
