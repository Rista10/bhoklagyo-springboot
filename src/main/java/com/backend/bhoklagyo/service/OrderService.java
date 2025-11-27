package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.order.CreateOrderDTO;
import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.mapper.OrderMapper;
import com.backend.bhoklagyo.model.*;
import com.backend.bhoklagyo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepo;
    private final RestaurantRepository restaurantRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final MenuItemRepository menuItemRepo;

    public OrderDTO createOrder(CreateOrderDTO dto) {

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        
            for (var itemDTO : dto.getItems()) {
                MenuItem menuItem = menuItemRepo.findById(itemDTO.getMenuItemId())
                        .orElseThrow(() -> new RuntimeException("Menu item not found"));

                OrderItem orderItem = new OrderItem();
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setUnitPrice(menuItem.getPrice());

                totalAmount += menuItem.getPrice() * itemDTO.getQuantity();
                orderItems.add(orderItem);
            }


        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderStatus("PENDING");
        order.setPaymentStatus("UNPAID");
        order.setTotalAmount(totalAmount);
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setCreatedAt(LocalDateTime.now());

        orderRepo.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepo.save(item);
        }
        return OrderMapper.toDTO(order);
    }

    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toDTO(order);
    }


    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(status);
        orderRepo.save(order);

        return OrderMapper.toDTO(order);
    }


    public List<OrderDTO> getOrdersByCustomer(Long customerId) {
        List<Order> orders = orderRepo.findByCustomerId(customerId);
        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

}
