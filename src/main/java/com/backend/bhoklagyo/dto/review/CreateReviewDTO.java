package com.backend.bhoklagyo.dto.review;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateReviewDTO {
    private int rating;
    private String comment;
}
