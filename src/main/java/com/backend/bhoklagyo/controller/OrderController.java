package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.order.CreateOrderDTO;
import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

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
    public OrderDTO create(@RequestBody CreateOrderDTO dto, Authentication authentication) {
    return orderService.createOrder(dto, authentication);
}
    @GetMapping("/{orderId}")
    public OrderDTO get(@PathVariable UUID orderId) {
        return orderService.getOrder(orderId);
    }

    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PatchMapping("/{orderId}/status")
    public OrderDTO updateStatus(
            @PathVariable UUID orderId,
            @RequestParam String status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(
            @PathVariable UUID customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }
}
