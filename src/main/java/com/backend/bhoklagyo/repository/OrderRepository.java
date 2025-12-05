package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.model.Restaurant;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByRestaurantId(UUID restaurantId);
    List<Order> findByUserId(UUID userId);
    List<Order> findByUserAndRestaurant(User user, Restaurant restaurant);

}
