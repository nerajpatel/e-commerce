package com.microshop.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(name = "uq_user_roles", columnNames = {"user_id", "role"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 32)
    private String role;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
