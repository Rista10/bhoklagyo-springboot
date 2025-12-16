package com.backend.bhoklagyo.dto.review;

import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private UUID id;
    private UUID userId;
    private UUID restaurantId;
    private String userEmail;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
