package com.backend.bhoklagyo.dto.review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long restaurantId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
