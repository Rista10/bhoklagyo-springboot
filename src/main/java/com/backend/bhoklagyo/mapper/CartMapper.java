package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.cart.AddCartItemDTO;
import com.backend.bhoklagyo.dto.cart.CartDTO;
import com.backend.bhoklagyo.dto.cart.CartItemDTO;
import com.backend.bhoklagyo.model.CartItem;
import org.springframework.stereotype.Component;
import com.backend.bhoklagyo.model.Cart;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());

        dto.setCartItems(cart.getCartItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList()));

        return dto;
    }


    public CartItemDTO toItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setMenuItemId(item.getMenuItem().getId());
        dto.setMenuItemName(item.getMenuItem().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getUnitPrice() * item.getQuantity());
        return dto;
    }

    // public CartItem toEntity(CartItemDTO dto) {
    //     CartItem item = new CartItem();
    //     item.setQuantity(dto.getQuantity());
    //     item.setUnitPrice(dto.getUnitPrice());
    //     return item;
    // }
}
