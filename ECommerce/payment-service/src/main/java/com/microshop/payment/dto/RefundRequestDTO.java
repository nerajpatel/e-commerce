package com.microshop.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundRequestDTO {
    @NotNull(message = "Refund amount required")
    private Double refundAmount;
}
