package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.owner.OwnerRequestDTO;
import com.backend.bhoklagyo.dto.owner.OwnerResponseDTO;
import com.backend.bhoklagyo.dto.delivery_person.DeliveryRequestDTO;
import com.backend.bhoklagyo.dto.delivery_person.DeliveryResponseDTO;
import com.backend.bhoklagyo.enums.Role;
import com.backend.bhoklagyo.model.Owner;
import com.backend.bhoklagyo.model.DeliveryPerson;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.RestaurantOwnerRepository;
import com.backend.bhoklagyo.repository.DeliveryPersonRepository;
import com.backend.bhoklagyo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RoleRequestController {

    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final UserRepository userRepository;

    @PostMapping("/owner")
    @Transactional
    public ResponseEntity<OwnerResponseDTO> requestOwner(
            @RequestBody OwnerRequestDTO dto,
            Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal(); 
        String email = jwt.getClaim("https://bhoklagyo.app/email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            return ResponseEntity.badRequest().build();
        }

        Owner owner = new Owner();
        owner.setUser(user);
        owner.setPanNumber(dto.getPanNumber());
        owner.setBankAccountNumber(dto.getBankAccountNumber());
        owner.setBankName(dto.getBankName());

        restaurantOwnerRepository.save(owner);

        // map to response DTO
        OwnerResponseDTO response = new OwnerResponseDTO(
                owner.getId(),
                owner.getPanNumber(),
                owner.getBankAccountNumber(),
                owner.getBankName(),
                owner.getCreatedAt(),
                owner.getUser().getId()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delivery")
    @Transactional
    public ResponseEntity<DeliveryResponseDTO> requestDelivery(
            @RequestBody DeliveryRequestDTO dto,
            Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal(); 
        String email = jwt.getClaim("https://bhoklagyo.app/email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            return ResponseEntity.badRequest().build();
        }

        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setUser(user);
        deliveryPerson.setLicenseNumber(dto.getLicenseNumber());
        deliveryPerson.setVehicleNumber(dto.getVehicleNumber());

        deliveryPersonRepository.save(deliveryPerson);

        DeliveryResponseDTO response = new DeliveryResponseDTO(
                deliveryPerson.getId(),
                deliveryPerson.getLicenseNumber(),
                deliveryPerson.getVehicleNumber(),
                deliveryPerson.getCreatedAt(),
                deliveryPerson.getUser().getId()
        );

        return ResponseEntity.ok(response);
    }
}
