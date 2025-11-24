package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.cart.AddCartItemDTO;
import com.backend.bhoklagyo.dto.cart.CartDTO;
import com.backend.bhoklagyo.dto.cart.CartItemDTO;
import com.backend.bhoklagyo.mapper.CartMapper;
import com.backend.bhoklagyo.model.Cart;
import com.backend.bhoklagyo.model.CartItem;
import com.backend.bhoklagyo.model.Customer;
import com.backend.bhoklagyo.model.MenuItem;
import com.backend.bhoklagyo.repository.CartItemRepository;
import com.backend.bhoklagyo.repository.CartRepository;
import com.backend.bhoklagyo.repository.CustomerRepository;
import com.backend.bhoklagyo.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CustomerRepository customerRepo;
    private final MenuItemRepository menuItemRepo;
    private final CartItemRepository cartItemRepo;
    private final CartMapper mapper;

    // Helper: create new cart if not exists
    private Cart getOrCreateCart(Long customerId) {
        Cart cart = cartRepo.findByCustomerId(customerId);

        if (cart == null) {
            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            cart = new Cart(null, customer, 0.0, new java.util.ArrayList<>());
            cartRepo.save(cart);
        }
        return cart;
    }

    // Helper: update total price
    private void updateCartTotal(Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        cart.setTotalPrice(total);
        cartRepo.save(cart);
    }

    // -------------------- Cart Methods --------------------

    public CartDTO getCart(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        return mapper.toDTO(cart);
    }

    public CartItemDTO addItem(Long customerId, AddCartItemDTO dto) {
        Cart cart = getOrCreateCart(customerId);

        MenuItem menuItem = menuItemRepo.findById(dto.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // Check if already present
        CartItem existingItem = cart.getCartItems().stream()
                .filter(i -> i.getMenuItem().getId().equals(dto.getMenuItemId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + dto.getQuantity());
            updateCartTotal(cart);
            return mapper.toItemDTO(cartItemRepo.save(existingItem));
        }

        // Add new item
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setMenuItem(menuItem);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(menuItem.getPrice());

        cart.getCartItems().add(item);
        cartItemRepo.save(item);
        updateCartTotal(cart);

        return mapper.toItemDTO(item);
    }

    public CartItemDTO updateItem(Long customerId, Long cartItemId, int quantity) {
        Cart cart = getOrCreateCart(customerId);

        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId()))
            throw new RuntimeException("Item does not belong to this cart");

        item.setQuantity(quantity);
        cartItemRepo.save(item);
        updateCartTotal(cart);

        return mapper.toItemDTO(item);
    }

    public void removeItem(Long customerId, Long cartItemId) {
        Cart cart = getOrCreateCart(customerId);

        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId()))
            throw new RuntimeException("Item does not belong to this cart");

        cart.getCartItems().remove(item);
        cartItemRepo.delete(item);
        updateCartTotal(cart);
    }

    public void clearCart(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        cart.getCartItems().clear();
        updateCartTotal(cart);
    }
}
