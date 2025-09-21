package com.microshop.payment.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private String method;
    private String status;
}
