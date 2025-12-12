package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import com.backend.bhoklagyo.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByRestaurantId(UUID restaurantId);
    List<Order> findByUserId(UUID userId);
    List<Order> findByUserAndRestaurant(User user, Restaurant restaurant);


    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurant.id = :restaurantId")
    long countTotalOrdersByRestaurant(UUID restaurantId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'COMPLETED' AND o.restaurant.id = :restaurantId")
    long countCompletedOrdersByRestaurant(UUID restaurantId);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'COMPLETED' AND o.restaurant.id = :restaurantId")
    Double completedRevenueByRestaurant(UUID restaurantId);

    @Query("""
        SELECT MONTH(o.createdAt) AS month, COUNT(o) AS total
        FROM Order o
        WHERE o.restaurant.id = :restaurantId
        GROUP BY MONTH(o.createdAt)
        ORDER BY month
    """)
    List<Object[]> monthlyRateByRestaurant(UUID restaurantId);

    List<Order> findByUserIdAndStatusNot(UUID userId, OrderStatus status);


}
