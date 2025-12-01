package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByAuth0Id(String auth0Id);
}
