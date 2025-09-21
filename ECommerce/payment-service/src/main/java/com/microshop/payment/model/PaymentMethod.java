package com.microshop.payment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType methodType; // CARD, UPI, WALLET

    private String provider; // e.g., VISA, PayTM

    private String accountNumber; // masked card no / UPI ID
}
