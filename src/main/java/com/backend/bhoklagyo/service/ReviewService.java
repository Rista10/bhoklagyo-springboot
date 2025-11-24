package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.review.CreateReviewDTO;
import com.backend.bhoklagyo.dto.review.ReviewDTO;
import com.backend.bhoklagyo.mapper.ReviewMapper;
import com.backend.bhoklagyo.model.Customer;
import com.backend.bhoklagyo.model.Restaurant;
import com.backend.bhoklagyo.model.Review;
import com.backend.bhoklagyo.repository.CustomerRepository;
import com.backend.bhoklagyo.repository.RestaurantRepository;
import com.backend.bhoklagyo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final CustomerRepository customerRepo;
    private final RestaurantRepository restaurantRepo;


    public ReviewDTO addReview(Long restaurantId, CreateReviewDTO dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Review review = ReviewMapper.toEntity(dto);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepo.save(review);

        return ReviewMapper.toDTO(review);
    }

    public List<ReviewDTO> getReviewsByRestaurant(Long restaurantId) {
        return reviewRepo.findByRestaurantId(restaurantId)
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}
