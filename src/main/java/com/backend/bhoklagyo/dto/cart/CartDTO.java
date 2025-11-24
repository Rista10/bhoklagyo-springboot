package com.backend.bhoklagyo.dto.cart;

import lombok.Data;
import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private Double totalPrice;
    private List<CartItemDTO> cartItems;
}
