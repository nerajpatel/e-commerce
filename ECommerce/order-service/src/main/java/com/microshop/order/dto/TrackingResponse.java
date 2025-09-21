// dto/response/TrackingResponse.java
package com.microshop.order.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TrackingResponse {
  private Long id;
  private Long orderId;
  private String courier;
  private String trackingNumber;
  private String status;
  private String note;
  private OffsetDateTime createdAt;
}
