package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
     @Query("""
    SELECT oi.menuItem.name, COUNT(oi)
    FROM OrderItem oi
    JOIN oi.order o
    JOIN o.restaurant r
    WHERE r.id = :restaurantId
    GROUP BY oi.menuItem.name
    ORDER BY COUNT(oi) DESC
""")
List<Object[]> findPopularFoodByRestaurant(UUID restaurantId);
}
