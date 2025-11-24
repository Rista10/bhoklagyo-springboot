package com.backend.bhoklagyo.dto.review;

import lombok.Data;

@Data
public class CreateReviewDTO {
    private Long customerId;
    private int rating;
    private String comment;
}
