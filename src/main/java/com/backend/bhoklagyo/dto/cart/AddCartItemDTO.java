package com.backend.bhoklagyo.dto.cart;

import lombok.Data;

@Data
public class AddCartItemDTO {
    private Long menuItemId;
    private int quantity;
}
