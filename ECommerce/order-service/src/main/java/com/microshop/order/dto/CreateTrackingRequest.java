// dto/request/CreateTrackingRequest.java
package com.microshop.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateTrackingRequest {
  @NotBlank private String courier;
  @NotBlank private String trackingNumber;
  @NotBlank private String status;
  private String note;
}
