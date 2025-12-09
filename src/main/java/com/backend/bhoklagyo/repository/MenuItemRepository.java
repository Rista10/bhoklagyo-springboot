package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    Page<MenuItem> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<MenuItem> findByCategoryIgnoreCase(String category, Pageable pageable);
    MenuItem findByIdAndRestaurantId(UUID id, UUID restaurantId);
    List<MenuItem> findAllByIdIn(List<UUID> ids);
    Page<MenuItem> findAll(Pageable pageable);


     @Query("""
    SELECT m FROM MenuItem m
    WHERE m.restaurant.id = :restaurantId
        AND (:category IS NULL OR m.category = :category)
        AND (:maxPrice IS NULL OR m.price <= :maxPrice)
        AND (:minPrice IS NULL OR m.price >= :minPrice)
        AND (:preparationTimeMins IS NULL OR m.preparationTimeMins <= :preparationTimeMins)
    """)
    Page<MenuItem> findFiltered(
            @Param("restaurantId") UUID restaurantId,
            @Param("category") String category,
            @Param("maxPrice") Double maxPrice,
            @Param("minPrice") Double minPrice,
            @Param("preparationTimeMins") Integer preparationTimeMins,
            Pageable pageable
    );


}
