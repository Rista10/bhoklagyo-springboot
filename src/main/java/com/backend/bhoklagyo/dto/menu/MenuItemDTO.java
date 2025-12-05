package com.backend.bhoklagyo.dto.menu;

import lombok.Data;
import java.util.UUID;

@Data
public class MenuItemDTO {

    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Integer preparationTimeMins;
    private boolean isVeg;
    private String imageKey;
    private UUID restaurantId;
}
