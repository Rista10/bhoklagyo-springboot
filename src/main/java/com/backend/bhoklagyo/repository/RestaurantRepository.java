package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByRestaurantNameContainingIgnoreCase(String name);

    List<Restaurant> findByCuisineTypeIgnoreCase(String category);

    List<Restaurant>
    findByRestaurantNameContainingIgnoreCaseAndCuisineTypeIgnoreCase(String name, String category);
}
