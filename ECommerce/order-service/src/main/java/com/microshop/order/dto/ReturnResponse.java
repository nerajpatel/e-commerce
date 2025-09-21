// dto/response/ReturnResponse.java
package com.microshop.order.dto;

import com.microshop.order.model.ReturnStatus;
import lombok.*;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReturnResponse {
  private Long id;
  private Long orderId;
  private ReturnStatus status;
  private String reason;
  private String resolutionNote;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
