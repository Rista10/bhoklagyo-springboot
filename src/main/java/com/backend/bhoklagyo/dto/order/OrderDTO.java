package com.backend.bhoklagyo.dto.order;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import com.backend.bhoklagyo.dto.address.DeliveryAddressResponseDTO;
import com.backend.bhoklagyo.dto.order.OrderItemDTO;
import com.backend.bhoklagyo.enums.OrderStatus;
    
import java.util.UUID;
@Data
public class OrderDTO {
    private UUID id;
    private OrderStatus status;
    private Double itemTotal;
    private Double deliveryCharge;
    private Double totalAmount;
    private String specialInstructions;
    private DeliveryAddressResponseDTO deliveryAddress;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}
