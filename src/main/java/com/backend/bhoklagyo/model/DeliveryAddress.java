package com.backend.bhoklagyo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "delivery_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;  

    private String municipality;

    @Column(name = "ward_number")
    private Integer wardNumber;

    @Column(name = "tole_name")
    private String toleName;

    private String landmark;
    private Double latitude;
    private Double longitude;
}
