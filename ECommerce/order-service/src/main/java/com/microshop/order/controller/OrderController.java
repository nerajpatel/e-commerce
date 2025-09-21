// controller/OrderController.java
package com.microshop.order.controller;

import com.microshop.order.dto.CreateOrderRequest;
import com.microshop.order.dto.OrderResponse;
import com.microshop.order.dto.PaymentStatusUpdateRequest;
import com.microshop.order.dto.UpdateOrderStatusRequest;
import com.microshop.order.model.OrderStatus;
import com.microshop.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public OrderResponse place(@Valid @RequestBody CreateOrderRequest req){
    return orderService.placeOrder(req);
  }

  @GetMapping("/{id}")
  public OrderResponse one(@PathVariable Long id){ return orderService.get(id); }

  @GetMapping
  public Page<OrderResponse> list(@RequestParam(defaultValue="0") int page,
                                  @RequestParam(defaultValue="10") int size,
                                  @RequestParam(required=false) Long userId,
                                  @RequestParam(required=false) OrderStatus status){
    return orderService.list(PageRequest.of(page,size), userId, status);
  }

  @DeleteMapping("/{id}")
  public void cancel(@PathVariable Long id){ orderService.cancel(id); }

  @PatchMapping("/{id}/status")
  public OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest req){
    return orderService.updateStatus(id, req);
  }

  @PatchMapping("/{id}/payment-status")
  public OrderResponse updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentStatusUpdateRequest req){
    return orderService.updatePaymentStatus(id, req);
  }
}
