// dto/request/CreateOrderRequest.java
package com.microshop.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateOrderRequest {
  @NotNull(message="userId required")
  private Long userId;

  @NotNull(message="shippingAddressId required")
  private Long shippingAddressId;

  private String note;

  @NotEmpty(message="items required")
  private List<@Valid OrderItemRequest> items;
}
