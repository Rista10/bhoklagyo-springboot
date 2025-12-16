package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable UUID restaurantId,
            @RequestBody CreateReviewDTO dto,
            Authentication authentication) {

        ReviewDTO createdReview = reviewService.addReview(restaurantId, dto, authentication);
        return ResponseEntity.status(201).body(createdReview); // 201 Created
    }

    // UPDATE REVIEW
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody CreateReviewDTO dto) {

        ReviewDTO updatedReview = reviewService.updateReview(reviewId, dto);
        return ResponseEntity.ok(updatedReview); // 200 OK
    }

    // DELETE REVIEW
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    // GET REVIEWS BY RESTAURANT
    @GetMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable UUID restaurantId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByRestaurant(restaurantId);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviews);
    }

    // GET SINGLE REVIEW
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable UUID reviewId) {
        ReviewDTO review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review); // 200 OK
    }
}
