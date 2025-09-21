package com.microshop.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating; // 1-5
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long userId; // from User Service
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
