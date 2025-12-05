package com.backend.bhoklagyo.dto.order;

import com.backend.bhoklagyo.dto.address.DeliveryAddressCreateDTO;
import lombok.Data;
import java.util.UUID;
import java.util.List;

@Data
public class CreateOrderDTO {
    private UUID restaurantId;
    private List<CreateOrderItemDTO> items;
    private String paymentStatus;
    private DeliveryAddressCreateDTO deliveryAddress;  
    private String specialInstructions;
}
