package com.microshop.inventory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementResponse {
    private Long id;
    private Long productId;
    private Integer changeQuantity;
    private String reason;
    private LocalDateTime createdAt;
}
