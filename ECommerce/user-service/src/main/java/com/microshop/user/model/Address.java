package com.microshop.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    private String label;
    @Column(nullable = false)
    private String line1;
    private String line2;
    @Column(nullable = false)
    private String city;
    private String state;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private Boolean isDefault = false;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();
}