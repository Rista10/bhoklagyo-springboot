package com.backend.bhoklagyo.dto.order;

import lombok.Data;
import java.util.UUID;

@Data
public class OrderItemDTO {

    private UUID id;             
    private UUID orderId;        
    private UUID menuItemId;     
    private String menuItemName; 
    private int quantity;        
    private Double unitPrice;    
    private Double totalPrice;   
}
