package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.model.Review;
import org.springframework.stereotype.Component;

public class ReviewMapper {
    private ReviewMapper() {}

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setCustomerId(review.getCustomer().getId());
        dto.setCustomerName(review.getCustomer().getName());
        dto.setRestaurantId(review.getRestaurant().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }

    public static Review toEntity(CreateReviewDTO dto) {
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }
}
