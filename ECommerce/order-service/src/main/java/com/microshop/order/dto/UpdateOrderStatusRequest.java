// dto/request/UpdateOrderStatusRequest.java
package com.microshop.order.dto;

import com.microshop.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOrderStatusRequest {
  @NotNull private OrderStatus status;
  private String note;
}
