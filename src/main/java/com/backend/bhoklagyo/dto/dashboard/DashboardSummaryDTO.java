package com.backend.bhoklagyo.dto.dashboard;

public record DashboardSummaryDTO(
        long totalOrders,
        long completedOrders,
        Double revenue
) {}

