package com.backend.bhoklagyo.controller;
import com.backend.bhoklagyo.dto.dashboard.DashboardSummaryDTO;
import com.backend.bhoklagyo.dto.dashboard.MonthlyRateDTO;
import com.backend.bhoklagyo.dto.dashboard.PopularFoodDTO;
import com.backend.bhoklagyo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;    
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> summary(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(dashboardService.getSummary(restaurantId));
    }

    @GetMapping("/order-rate")
    public ResponseEntity<List<MonthlyRateDTO>> orderRate(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(dashboardService.getMonthlyRate(restaurantId));
    }

    @GetMapping("/popular-food")
    public ResponseEntity<List<PopularFoodDTO>> popularFood(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(dashboardService.getPopularFood(restaurantId));
    }
}
