package com.backend.bhoklagyo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.backend.bhoklagyo.enums.Role;
import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; 

    private String auth0Id;

    @Column(name = "full_name")
    private String name;

    private String email;

    @Column(nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "default_lat",nullable = true)
    private Double defaultLat;

    @Column(name = "default_long",nullable = true)
    private Double defaultLong;

    @Column(name = "default_address",nullable = true)
    private String defaultAddress;

    
}
