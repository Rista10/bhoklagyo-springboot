package com.backend.bhoklagyo.dto.order;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long menuItemId;
    private String menuItemName;
    private int quantity;
    private Double unitPrice;
}
