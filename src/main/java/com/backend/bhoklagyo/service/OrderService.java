package com.backend.bhoklagyo.service;

import com.backend.bhoklagyo.dto.order.CreateOrderDTO;
import com.backend.bhoklagyo.dto.order.CreateOrderItemDTO;
import com.backend.bhoklagyo.dto.order.OrderDTO;
import com.backend.bhoklagyo.mapper.OrderMapper;
import com.backend.bhoklagyo.model.*;
import com.backend.bhoklagyo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import com.backend.bhoklagyo.enums.OrderStatus;
import com.backend.bhoklagyo.mapper.DeliveryAddressMapper;
import com.backend.bhoklagyo.exception.NoSuchCustomerExistsException;
import com.backend.bhoklagyo.exception.InvalidInputException;
import com.backend.bhoklagyo.service.AuthService;
import com.backend.bhoklagyo.model.User;
import com.backend.bhoklagyo.repository.UserRepository;
import com.backend.bhoklagyo.service.SSEEmitterService;


import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestaurantRepository restaurantRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final MenuItemRepository menuItemRepo;
    private final DeliveryAddressRepository deliveryAddressRepo;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final SSEEmitterService sseEmitterService;
    private final NotificationRepository notificationRepo;

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto, Authentication authentication) {

        User user = authService.getCurrentUser(authentication);

        if (user.getRole() != com.backend.bhoklagyo.enums.Role.CUSTOMER) {
            throw new RuntimeException("Only customers can create orders.");
        }

        Restaurant restaurant = restaurantRepo.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        List<UUID> menuItemIds = dto.getItems().stream()
                .map(CreateOrderItemDTO::getMenuItemId)
                .toList();

        Map<UUID, MenuItem> menuItemMap = menuItemRepo.findAllByIdIn(menuItemIds)
                .stream()
                .collect(Collectors.toMap(MenuItem::getId, Function.identity()));

        List<OrderItem> orderItems = new ArrayList<>();
        double itemTotal = 0.0;

        for (var itemDTO : dto.getItems()) {
            MenuItem menuItem = menuItemMap.get(itemDTO.getMenuItemId());

            if (menuItem == null) {
                throw new RuntimeException("Menu item not found: " + itemDTO.getMenuItemId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(menuItem.getPrice());

            double lineTotal = menuItem.getPrice() * itemDTO.getQuantity();
            itemTotal += lineTotal;

            orderItems.add(orderItem);
        }

        double deliveryCharge = 50.0;

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PENDING);
        order.setItemTotal(itemTotal);
        order.setDeliveryCharge(deliveryCharge);
        order.setTotalAmount(itemTotal + deliveryCharge);
        order.setSpecialInstructions(dto.getSpecialInstructions());
        order.setCreatedAt(LocalDateTime.now());

        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepo.save(order);

        DeliveryAddress address = DeliveryAddressMapper.toEntity(dto.getDeliveryAddress(), order);
        deliveryAddressRepo.save(address);
        order.setDeliveryAddress(address);

        return OrderMapper.toDTO(order);
    }



    public OrderDTO getOrder(UUID orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toDTO(order);
    }

    public List<UUID> getNonCompletedOrderIdsByUser(UUID userId) {
        return orderRepo.findByUserIdAndStatusNot(userId, OrderStatus.COMPLETED)
                .stream()
                .map(Order::getId)
                .toList();
    }


    public OrderDTO updateOrderStatus(UUID orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());

        // 🔒 Enforce allowed transitions
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                "Cannot change order status from " + order.getStatus() + " to " + newStatus
            );
        }

        order.setStatus(newStatus);
        orderRepo.save(order);

         // Notify only the order owner
        sseEmitterService.sendUpdate(order);

        Notification notif = new Notification();
        notif.setUser(order.getUser());
        notif.setTitle("Order update");
        notif.setMessage("Order " + order.getId() + " is now " + newStatus);
        notif.setCreatedAt(LocalDateTime.now());
        notif.setRead(false);

        notificationRepo.save(notif);

    
        return OrderMapper.toDTO(order);
    }



    public List<OrderDTO> getOrdersByCustomer(UUID customerId) {
        List<Order> orders = orderRepo.findByUserId(customerId);
        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    public List<OrderDTO> getOrdersByRestaurant(UUID restaurantId) {
    List<Order> orders = orderRepo.findByRestaurantId(restaurantId);
    return orders.stream()
                 .map(OrderMapper::toDTO)
                 .collect(Collectors.toList());
}

}
