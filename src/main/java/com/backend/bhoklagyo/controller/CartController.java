package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.cart.AddCartItemDTO;
import com.backend.bhoklagyo.dto.cart.CartDTO;
import com.backend.bhoklagyo.dto.cart.CartItemDTO;
import com.backend.bhoklagyo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers/{customerId}/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDTO getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId);
    }

    @PostMapping("/items")
    public CartItemDTO addItem(@PathVariable Long customerId,
                               @RequestBody AddCartItemDTO dto) {
        return cartService.addItem(customerId, dto);
    }

    @PutMapping("/items/{cartItemId}")
    public CartItemDTO updateItem(@PathVariable Long customerId,
                                  @PathVariable Long cartItemId,
                                  @RequestParam int quantity) {
        return cartService.updateItem(customerId, cartItemId, quantity);
    }

    @DeleteMapping("/items/{cartItemId}")
    public void removeItem(@PathVariable Long customerId,
                           @PathVariable Long cartItemId) {
        cartService.removeItem(customerId, cartItemId);
    }

    @DeleteMapping
    public void clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
    }
}
