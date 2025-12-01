package com.backend.bhoklagyo.dto.order;

import com.backend.bhoklagyo.dto.address.DeliveryAddressCreateDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    private Long customerId;
    private List<CreateOrderItemDTO> items;
    private String paymentStatus;
    private DeliveryAddressCreateDTO deliveryAddress;  
}
