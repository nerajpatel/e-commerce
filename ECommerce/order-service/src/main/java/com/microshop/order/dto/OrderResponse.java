// dto/response/OrderResponse.java
package com.microshop.order.dto;

import com.microshop.order.model.OrderStatus;
import com.microshop.order.model.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
  private Long id;
  private Long userId;
  private Long shippingAddressId;
  private OrderStatus status;
  private PaymentStatus paymentStatus;
  private String paymentReference;
  private BigDecimal subtotal;
  private BigDecimal shippingFee;
  private BigDecimal total;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private List<OrderItemResponse> items;
}
