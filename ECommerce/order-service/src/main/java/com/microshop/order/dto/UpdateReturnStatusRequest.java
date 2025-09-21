// dto/request/UpdateReturnStatusRequest.java
package com.microshop.order.dto;

import com.microshop.order.model.ReturnStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateReturnStatusRequest {
  @NotNull private ReturnStatus status;
  private String resolutionNote;
}
