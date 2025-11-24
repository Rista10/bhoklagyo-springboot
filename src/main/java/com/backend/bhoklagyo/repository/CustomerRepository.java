package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can add custom queries here if needed, e.g., find by email
}
