package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.owner.OwnerRequestDTO;
import com.backend.bhoklagyo.dto.owner.OwnerResponseDTO;
import com.backend.bhoklagyo.dto.delivery_person.DeliveryRequestDTO;
import com.backend.bhoklagyo.dto.delivery_person.DeliveryResponseDTO;
import com.backend.bhoklagyo.service.RoleRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role-requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RoleRequestController {

    private final RoleRequestService roleRequestService;

    @PostMapping("/owner")
    public ResponseEntity<OwnerResponseDTO> requestOwner(
            @RequestBody OwnerRequestDTO dto,
            Authentication auth) {

        String email = ((Jwt) auth.getPrincipal()).getClaim("https://bhoklagyo.app/email");
        OwnerResponseDTO response = roleRequestService.requestOwner(email, dto);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/delivery")
    public ResponseEntity<DeliveryResponseDTO> requestDelivery(
            @RequestBody DeliveryRequestDTO dto,
            Authentication auth) {

        String email = ((Jwt) auth.getPrincipal()).getClaim("https://bhoklagyo.app/email");
        DeliveryResponseDTO response = roleRequestService.requestDelivery(email, dto);
        return ResponseEntity.status(201).body(response);
    }

}
