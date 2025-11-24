package com.backend.bhoklagyo.dto.order;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrderDTO {
    private Long customerId;
    private String deliveryAddress;
    private String paymentMethod; 
    private List<CreateOrderItemDTO> items;
}
