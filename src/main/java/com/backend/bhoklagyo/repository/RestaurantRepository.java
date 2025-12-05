package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.backend.bhoklagyo.model.Owner;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByRestaurantNameContainingIgnoreCase(String name);

    List<Restaurant> findByCuisineTypeIgnoreCase(String category);

    List<Restaurant> findByOwner(Owner owner);

    Page<Restaurant> findAll(Pageable pageable);
    

    @Query(value = """
    SELECT r.* FROM restaurants r
    WHERE (
    6371 * acos(
        cos(radians(:lat)) * cos(radians(r.latitude))
        * cos(radians(r.longitude) - radians(:lng))
        + sin(radians(:lat)) * sin(radians(r.latitude))
    )
    ) < :distance
    """, nativeQuery = true)
    List<Restaurant> findNearby(@Param("lat") Double lat,
                                @Param("lng") Double lng,
                                @Param("distance") Double distance);


}
