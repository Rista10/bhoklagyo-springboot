package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
