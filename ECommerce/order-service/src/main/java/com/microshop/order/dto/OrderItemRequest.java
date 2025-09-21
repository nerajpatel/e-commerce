// dto/request/OrderItemRequest.java
package com.microshop.order.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemRequest {
  @NotNull(message="productId required")
  private Long productId;

  @NotNull @Min(value=1, message="quantity >= 1")
  private Integer quantity;
}
