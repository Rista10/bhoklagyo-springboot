package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.mapper.ReviewMapper;
import com.backend.bhoklagyo.model.Restaurant;
import com.backend.bhoklagyo.model.Review;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.RestaurantRepository;
import com.backend.bhoklagyo.repository.ReviewRepository;
import com.backend.bhoklagyo.repository.UserRepository;
import com.backend.bhoklagyo.model.Order;
import com.backend.bhoklagyo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.backend.bhoklagyo.service.AuthService;
import java.util.Comparator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final RestaurantRepository restaurantRepo;
    private final UserRepository userRepository;
    private final OrderRepository orderRepo;
    private final AuthService authService;

    // CREATE
    public ReviewDTO addReview(UUID restaurantId, CreateReviewDTO dto, Authentication authentication) {
        User user = authService.getCurrentUser(authentication);
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Check user orders for this restaurant
        List<Order> orders = orderRepo.findByUserAndRestaurant(user, restaurant);
        if (orders.isEmpty()) {
            throw new RuntimeException("Cannot post review. User has not ordered from this restaurant.");
        }

        // Link review to the latest order (optional)
        Order latestOrder = orders.stream()
                .max(Comparator.comparing(Order::getCreatedAt))
                .orElseThrow();

        Review review = ReviewMapper.toEntity(dto);
        review.setUser(user);
        review.setRestaurant(restaurant);
        review.setOrder(latestOrder); 
        review.setCreatedAt(LocalDateTime.now());

        reviewRepo.save(review);
        return ReviewMapper.toDTO(review);
    }


    // UPDATE
    public ReviewDTO updateReview(UUID reviewId, CreateReviewDTO dto) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        reviewRepo.save(review);
        return ReviewMapper.toDTO(review);
    }

    // DELETE
    public void deleteReview(UUID reviewId) {
        if (!reviewRepo.existsById(reviewId)) {
            throw new RuntimeException("Review not found");
        }
        reviewRepo.deleteById(reviewId);
    }

    // GET BY RESTAURANT
    public List<ReviewDTO> getReviewsByRestaurant(UUID restaurantId) {
        return reviewRepo.findByRestaurantId(restaurantId)
                .stream()
                .map(ReviewMapper::toDTO)
                .toList();
    }

    // GET SINGLE REVIEW
    public ReviewDTO getReviewById(UUID reviewId) {
        return reviewRepo.findById(reviewId)
                .map(ReviewMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}
