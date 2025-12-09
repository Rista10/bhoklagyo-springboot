// package com.backend.bhoklagyo.config;

// import com.backend.bhoklagyo.dto.address.DeliveryAddressCreateDTO;
// import com.backend.bhoklagyo.enums.OrderStatus;
// import com.backend.bhoklagyo.model.*;
// import com.backend.bhoklagyo.repository.*;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;
// import lombok.AllArgsConstructor;
// import org.springframework.beans.factory.annotation.Value;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Random;

// @Component
// @AllArgsConstructor
// public class DatabaseSeeder implements CommandLineRunner {

//     private final UserRepository userRepository;
//     private final RestaurantRepository restaurantRepository;
//     private final OrderRepository orderRepository;
//     private final MenuItemRepository menuItemRepository;



//     @Override
//     @Transactional
//     public void run(String... args) throws Exception {

//         List<User> users = userRepository.findAll();
//         List<Restaurant> restaurants = restaurantRepository.findAll();
//         List<MenuItem> menuItems = menuItemRepository.findAll();

//         if(users.isEmpty() || restaurants.isEmpty()){
//             System.out.println("Please ensure there are users and restaurants in the database.");
//             return;
//         }

//         Random random = new Random();

//         // Simulate orders for the past 100 days
//         for(int daysAgo = 100; daysAgo >= 1; daysAgo--){
//             // Random number of orders per day (0-5)
//             int ordersToday = random.nextInt(2);
//             for(int i = 0; i < ordersToday; i++){
//                 User user = users.get(random.nextInt(users.size()));
//                 Restaurant restaurant = restaurants.get(random.nextInt(restaurants.size()));

//                 Order order = new Order();
//                 order.setUser(user);
//                 order.setRestaurant(restaurant);
//                 order.setStatus(OrderStatus.values()[random.nextInt(OrderStatus.values().length)]); // random status

//                 double itemTotal = 100 + random.nextInt(900); // random between 100-1000
//                 double deliveryCharge = 50;
//                 order.setItemTotal(itemTotal);
//                 order.setDeliveryCharge(deliveryCharge);
//                 order.setTotalAmount(itemTotal + deliveryCharge);
//                 order.setSpecialInstructions("Seed order");

//                 // Set createdAt for historical date
//                 order.setCreatedAt(LocalDateTime.now().minusDays(daysAgo));

//                 // Add OrderItem
//                 OrderItem item = new OrderItem();
//                 item.setOrder(order);
//                 item.setQuantity(1 + random.nextInt(3));
//                 item.setUnitPrice(itemTotal);
//                 item.setTotalItemPrice(itemTotal * item.getQuantity());
//                 MenuItem menuItem = menuItems.get(random.nextInt(menuItems.size()));
//                 item.setMenuItem(menuItem);
//                 order.getOrderItems().add(item);

//                 // Add DeliveryAddress using DTO
//                 DeliveryAddressCreateDTO addressDTO = new DeliveryAddressCreateDTO();
//                 addressDTO.setOrderId(order.getId());
//                 addressDTO.setMunicipality("Kathmandu");
//                 addressDTO.setWardNumber(5 + random.nextInt(5));
//                 addressDTO.setToleName("Thamel");
//                 addressDTO.setLandmark("Near Landmark " + i);
//                 addressDTO.setLatitude(27.7172 + random.nextDouble() * 0.01);
//                 addressDTO.setLongitude(85.3240 + random.nextDouble() * 0.01);

//                 DeliveryAddress address = new DeliveryAddress();
//                 address.setOrder(order);
//                 address.setMunicipality(addressDTO.getMunicipality());
//                 address.setWardNumber(addressDTO.getWardNumber());
//                 address.setToleName(addressDTO.getToleName());
//                 address.setLandmark(addressDTO.getLandmark());
//                 address.setLatitude(addressDTO.getLatitude());
//                 address.setLongitude(addressDTO.getLongitude());
//                 order.setDeliveryAddress(address);

//                 // Save order (cascades to items and address)
//                 orderRepository.save(order);
//             }
//         }

//         System.out.println("Database seeded successfully for the past year!");
//     }
// }
