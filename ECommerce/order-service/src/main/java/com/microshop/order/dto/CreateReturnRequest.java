// dto/request/CreateReturnRequest.java
package com.microshop.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateReturnRequest {
  @NotBlank private String reason;
}
