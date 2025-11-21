package com.backend.bhoklagyo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    @ElementCollection
    @CollectionTable(name = "customer_delivery_addresses", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "address")
    private List<String> deliveryAddresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderHistory;

    // @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private Cart cart;

    public Customer(String name, String email, Long phoneNumber, List<String> deliveryAddresses) {
        super(name, email, phoneNumber);
        this.deliveryAddresses = deliveryAddresses;
    }
}
