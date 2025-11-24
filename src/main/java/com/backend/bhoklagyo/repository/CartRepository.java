package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(Long customerId);
}
