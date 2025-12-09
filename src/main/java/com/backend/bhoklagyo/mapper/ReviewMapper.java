package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.model.Review;

public class ReviewMapper {
    private ReviewMapper() {}

    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setUserId(review.getUser() != null ? review.getUser().getId() : null);
        dto.setRestaurantId(review.getRestaurant() != null ? review.getRestaurant().getId() : null);
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setOrderId(review.getOrder() != null ? review.getOrder().getId() : null); // null-safe
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
