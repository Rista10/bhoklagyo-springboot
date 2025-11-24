package com.backend.bhoklagyo.dto.order;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long customerId;
    private String orderStatus;
    private String paymentStatus;
    private Double totalAmount;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}
