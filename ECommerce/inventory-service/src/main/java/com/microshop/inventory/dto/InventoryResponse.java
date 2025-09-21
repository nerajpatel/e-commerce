package com.microshop.inventory.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long productId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
}
