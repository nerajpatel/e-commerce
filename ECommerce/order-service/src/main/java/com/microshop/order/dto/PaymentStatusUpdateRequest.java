// dto/request/PaymentStatusUpdateRequest.java
package com.microshop.order.dto;

import com.microshop.order.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentStatusUpdateRequest {
  @NotNull private PaymentStatus paymentStatus;
  private String paymentReference;
}
