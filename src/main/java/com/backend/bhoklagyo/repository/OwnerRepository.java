package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.bhoklagyo.model.User;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    // You can add custom queries here if needed, e.g., find by email
    Optional<Owner> findByUser(User user);

}
