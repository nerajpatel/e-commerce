// client/InventoryClient.java
package com.microshop.order.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="inventory-service")
public interface InventoryClient {
  @GetMapping("/inventory/check/{productId}")
  ApiResponse<Boolean> check(@PathVariable Long productId, @RequestParam("qty") int qty);

  @PatchMapping("/inventory/{productId}/decrease")
  ApiResponse<Integer> decrease(@PathVariable Long productId,
                                @RequestParam @Valid @Min(1) Integer qty);

  @PostMapping("/inventory/increase/{productId}")
  ApiResponse<Integer> increase(@PathVariable Long productId, @RequestParam("qty") int qty);

  record ApiResponse<T>(boolean success, String message, T data) {}
}
