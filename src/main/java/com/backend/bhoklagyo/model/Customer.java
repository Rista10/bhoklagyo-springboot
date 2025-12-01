package com.backend.bhoklagyo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    @Column(name = "default_lat",nullable = true)
    private Double defaultLat;

    @Column(name = "default_long",nullable = true)
    private Double defaultLong;

    @Column(name = "default_address",nullable = true)
    private String defaultAddress;

}

