// service/OrderService.java
package com.microshop.order.service;

import com.microshop.order.dto.CreateOrderRequest;
import com.microshop.order.dto.OrderResponse;
import com.microshop.order.dto.PaymentStatusUpdateRequest;
import com.microshop.order.dto.UpdateOrderStatusRequest;
import com.microshop.order.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
  OrderResponse placeOrder(CreateOrderRequest req);
  OrderResponse get(Long id);
  Page<OrderResponse> list(Pageable pageable, Long userId, OrderStatus status);
  void cancel(Long id); // user or admin
  OrderResponse updateStatus(Long id, UpdateOrderStatusRequest req);
  OrderResponse updatePaymentStatus(Long id, PaymentStatusUpdateRequest req);
}
