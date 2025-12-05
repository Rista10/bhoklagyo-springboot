package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    // CREATE REVIEW
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ReviewDTO addReview(@PathVariable UUID restaurantId,
                               @RequestBody CreateReviewDTO dto,
                               Authentication authentication) {
        return reviewService.addReview(restaurantId, dto, authentication);
    }

    // UPDATE REVIEW
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/reviews/{reviewId}")
    public ReviewDTO updateReview(@PathVariable UUID reviewId,
                                  @RequestBody CreateReviewDTO dto) {
        return reviewService.updateReview(reviewId, dto);
    }

    // DELETE REVIEW
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/reviews/{reviewId}")
    public void deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
    }

    // GET REVIEWS BY RESTAURANT
    @GetMapping("/restaurants/{restaurantId}/reviews")
    public List<ReviewDTO> getReviews(@PathVariable UUID restaurantId) {
        return reviewService.getReviewsByRestaurant(restaurantId);
    }

    // GET SINGLE REVIEW
    @GetMapping("/reviews/{reviewId}")
    public ReviewDTO getReview(@PathVariable UUID reviewId) {
        return reviewService.getReviewById(reviewId);
    }
}
