package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.order.CreateOrderDTO;
import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;

import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody CreateOrderDTO dto, Authentication authentication) {
        OrderDTO createdOrder = orderService.createOrder(dto, authentication);
        return ResponseEntity.status(201).body(createdOrder); 
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> get(@PathVariable UUID orderId) {
        OrderDTO order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order); 
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<UUID>> getActiveOrderIdsByUser(@PathVariable UUID userId) {
        List<UUID> activeOrders = orderService.getNonCompletedOrderIdsByUser(userId);
        if (activeOrders.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(activeOrders);
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateStatus(
            @PathVariable UUID orderId,
            @RequestParam String status
    ) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder); 
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable UUID customerId) {
        List<OrderDTO> orders = orderService.getOrdersByCustomer(customerId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(orders);
    }
}
