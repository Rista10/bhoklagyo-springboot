package com.backend.bhoklagyo.repository;

import com.backend.bhoklagyo.model.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, UUID> {
}
