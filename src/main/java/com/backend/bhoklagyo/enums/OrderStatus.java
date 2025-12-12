package com.backend.bhoklagyo.enums;
import lombok.Getter;

public enum OrderStatus {

    PENDING,
    PREPARING,
    CONFIRMED,
    COMPLETED,
    PICKED_UP,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == PREPARING || newStatus == CANCELLED;
            case PREPARING -> newStatus == COMPLETED || newStatus == CANCELLED;
            case COMPLETED -> newStatus == PICKED_UP || newStatus == DELIVERED;
            case PICKED_UP -> newStatus == DELIVERED;
            default -> false;
        };
    }
}
