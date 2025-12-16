package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.delivery_person.DeliveryRequestDTO;
import com.backend.bhoklagyo.dto.delivery_person.DeliveryResponseDTO;
import com.backend.bhoklagyo.dto.owner.OwnerRequestDTO;
import com.backend.bhoklagyo.dto.owner.OwnerResponseDTO;
import com.backend.bhoklagyo.enums.Role;
import com.backend.bhoklagyo.model.DeliveryPerson;
import com.backend.bhoklagyo.model.Owner;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.DeliveryPersonRepository;
import com.backend.bhoklagyo.repository.RestaurantOwnerRepository;
import com.backend.bhoklagyo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleRequestService {

    private final UserRepository userRepository;
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;

    @Transactional
    public OwnerResponseDTO requestOwner(String email, OwnerRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new IllegalArgumentException("Only customers can request owner role");
        }

        Owner owner = new Owner();
        owner.setUser(user);
        owner.setPanNumber(dto.getPanNumber());
        owner.setBankAccountNumber(dto.getBankAccountNumber());
        owner.setBankName(dto.getBankName());

        restaurantOwnerRepository.save(owner);

        return new OwnerResponseDTO(
                owner.getId(),
                owner.getPanNumber(),
                owner.getBankAccountNumber(),
                owner.getBankName(),
                owner.getCreatedAt(),
                owner.getUser().getId()
        );
    }

    @Transactional
    public DeliveryResponseDTO requestDelivery(String email, DeliveryRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new IllegalArgumentException("Only customers can request delivery role");
        }

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setUser(user);
        deliveryPerson.setLicenseNumber(dto.getLicenseNumber());
        deliveryPerson.setVehicleNumber(dto.getVehicleNumber());

        deliveryPersonRepository.save(deliveryPerson);

        return new DeliveryResponseDTO(
                deliveryPerson.getId(),
                deliveryPerson.getLicenseNumber(),
                deliveryPerson.getVehicleNumber(),
                deliveryPerson.getCreatedAt(),
                deliveryPerson.getUser().getId()
        );
    }
}
