package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);

    MenuItem findByIdAndRestaurantId(Long id, Long restaurantId);
}
