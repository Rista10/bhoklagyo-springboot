package com.backend.bhoklagyo.repository;
import com.backend.bhoklagyo.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOwnerRepository extends JpaRepository<Owner, Long> {
}
