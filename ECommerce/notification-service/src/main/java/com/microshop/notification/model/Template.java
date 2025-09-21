package com.microshop.notification.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // ORDER_PLACED, PAYMENT_SUCCESS, etc.

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;
}
