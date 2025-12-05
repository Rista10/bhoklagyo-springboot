package com.backend.bhoklagyo.dto.order;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateOrderItemDTO {
    private UUID menuItemId;
    private int quantity;
}
