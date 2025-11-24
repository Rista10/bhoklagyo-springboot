package com.backend.bhoklagyo.controller;

import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewDTO addReview(@PathVariable Long restaurantId,
                               @RequestBody CreateReviewDTO dto) {
        return reviewService.addReview(restaurantId, dto);
    }

    @GetMapping
    public List<ReviewDTO> getReviews(@PathVariable Long restaurantId) {
        return reviewService.getReviewsByRestaurant(restaurantId);
    }
}
