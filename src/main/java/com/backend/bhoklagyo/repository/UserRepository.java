package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByAuth0Id(String auth0Id);

    boolean existsByAuth0Id(String auth0Id);
}
