package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.dto.order.OrderItemDTO;
import com.backend.bhoklagyo.model.Order;
import com.backend.bhoklagyo.model.OrderItem;

import java.util.stream.Collectors;

public class OrderMapper {

    private OrderMapper() {}

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryAddress(
    DeliveryAddressMapper.toResponseDTO(order.getDeliveryAddress())
);

        dto.setCreatedAt(order.getCreatedAt());

        dto.setItems(
                order.getOrderItems()
                        .stream()
                        .map(OrderMapper::toItemDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public static OrderItemDTO toItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setMenuItemId(item.getMenuItem().getId());
        dto.setMenuItemName(item.getMenuItem().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }
}
