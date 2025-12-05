package com.backend.bhoklagyo.repository;
import com.backend.bhoklagyo.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, UUID> {
}