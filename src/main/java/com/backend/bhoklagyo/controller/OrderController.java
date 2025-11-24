package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.order.CreateOrderDTO;
import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDTO create(@RequestBody CreateOrderDTO dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/{orderId}")
    public OrderDTO get(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PutMapping("/{orderId}/status")
    public OrderDTO updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @DeleteMapping("/{orderId}")
    public void delete(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(
            @PathVariable Long customerId) {
        List<OrderDTO> orders = orderService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }


    // @GetMapping("/restaurant/{restaurantId}")
    // public List<OrderDTO> getAllForRestaurant(@PathVariable Long restaurantId) {
    //     return orderService.getOrdersByRestaurant(restaurantId);
    // }
}
