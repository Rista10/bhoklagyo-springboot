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

    @Query("""
        SELECT m FROM MenuItem m
        WHERE (:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:veg IS NULL OR m.veg = :veg)
          AND (:category IS NULL OR LOWER(m.category) = LOWER(:category))
    """)
    Page<MenuItem> filterMenuItems(
            @Param("name") String name,
            @Param("veg") Boolean veg,
            @Param("category") String category,
            Pageable pageable
    );
    List<MenuItem> findAllByIdIn(List<UUID> ids);

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
