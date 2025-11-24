package com.backend.bhoklagyo.dto.cart;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long menuItemId;
    private String menuItemName;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice;
}
