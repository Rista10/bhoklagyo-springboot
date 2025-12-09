package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.dashboard.DashboardSummaryDTO;
import com.backend.bhoklagyo.dto.dashboard.MonthlyRateDTO;
import com.backend.bhoklagyo.dto.dashboard.PopularFoodDTO;
import com.backend.bhoklagyo.repository.OrderItemRepository;
import com.backend.bhoklagyo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    public DashboardSummaryDTO getSummary(UUID restaurantId) {

        long total = orderRepo.countTotalOrdersByRestaurant(restaurantId);
        long completed = orderRepo.countCompletedOrdersByRestaurant(restaurantId);
        Double revenue = orderRepo.completedRevenueByRestaurant(restaurantId);

        return new DashboardSummaryDTO(total, completed, revenue);
    }

    public List<MonthlyRateDTO> getMonthlyRate(UUID restaurantId) {
        return orderRepo.monthlyRateByRestaurant(restaurantId)
                .stream()
                .map(r -> new MonthlyRateDTO(
                        ((Number) r[0]).intValue(),  // month
                        ((Number) r[1]).longValue()  // count
                ))
                .toList();
    }

    public List<PopularFoodDTO> getPopularFood(UUID restaurantId) {
        return orderItemRepo.findPopularFoodByRestaurant(restaurantId)
                .stream()
                .map(r -> new PopularFoodDTO(
                        r[0].toString(),             // food name
                        ((Number) r[1]).longValue()  // count
                ))
                .toList();
    }
}
