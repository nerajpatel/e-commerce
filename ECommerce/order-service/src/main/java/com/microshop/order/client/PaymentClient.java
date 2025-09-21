// client/PaymentClient.java
package com.microshop.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="payment-service", path="/payments")
public interface PaymentClient {
  @GetMapping("/{orderId}")
  PaymentDTO getByOrderId(@PathVariable("orderId") Long orderId);

  @PostMapping("/initiate/{orderId}")
  PaymentDTO initiate(@PathVariable("orderId") Long orderId, @RequestParam("amount") java.math.BigDecimal amount);

  record PaymentDTO(Long orderId, java.math.BigDecimal amount, String status, String reference) {}
}
