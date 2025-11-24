package com.backend.bhoklagyo.dto.order;

import lombok.Data;

@Data
public class CreateOrderItemDTO {
    private Long menuItemId;
    private int quantity;
}
