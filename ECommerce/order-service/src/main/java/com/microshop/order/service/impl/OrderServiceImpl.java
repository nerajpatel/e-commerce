// service/impl/OrderServiceImpl.java
package com.microshop.order.service.impl;

import com.microshop.order.client.InventoryClient;
import com.microshop.order.client.PaymentClient;
import com.microshop.order.client.ProductClient;
import com.microshop.order.dto.*;
import com.microshop.order.exception.BusinessException;
import com.microshop.order.exception.ResourceNotFoundException;
import com.microshop.order.model.Order;
import com.microshop.order.model.OrderItem;
import com.microshop.order.model.OrderStatus;
import com.microshop.order.model.PaymentStatus;
import com.microshop.order.repository.OrderItemRepository;
import com.microshop.order.repository.OrderRepository;
import com.microshop.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository itemRepository;
  private final ProductClient productClient;
  private final InventoryClient inventoryClient;
  private final PaymentClient paymentClient;

  public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository itemRepository, ProductClient productClient, InventoryClient inventoryClient, PaymentClient paymentClient) {
    this.orderRepository = orderRepository;
    this.itemRepository = itemRepository;
    this.productClient = productClient;
    this.inventoryClient = inventoryClient;
    this.paymentClient = paymentClient;
  }

  @Override
  @Transactional
  public OrderResponse placeOrder(CreateOrderRequest req) {
    // 1) Build items with product info and compute totals
    BigDecimal subtotal = BigDecimal.ZERO;
    Order order = Order.builder()
            .userId(req.getUserId())
            .shippingAddressId(req.getShippingAddressId())
            .status(OrderStatus.CREATED)
            .paymentStatus(PaymentStatus.PENDING)
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .subtotal(BigDecimal.ZERO)
            .shippingFee(new BigDecimal("0.00")) // compute by rule if needed
            .total(BigDecimal.ZERO)
            .build();

    for (var it : req.getItems()) {
      var p = productClient.getProduct(it.getProductId());
      if (p == null) throw new ResourceNotFoundException("Product not found: " + it.getProductId());

      // Check stock
//      var ok = inventoryClient.check(p.id(), it.getQuantity());
//      if (ok == null || !Boolean.TRUE.equals(ok.data())) {
//        throw new BusinessException("Insufficient stock for product " + p.id());
//      }

      BigDecimal lineTotal = p.price().multiply(BigDecimal.valueOf(it.getQuantity()));
      subtotal = subtotal.add(lineTotal);

      order.getItems().add(OrderItem.builder()
              .order(order)
              .productId(p.id())
              .productName(p.name())
              .unitPrice(p.price())
              .quantity(it.getQuantity())
              .lineTotal(lineTotal)
              .build());
    }

    order.setSubtotal(subtotal);
    order.setTotal(subtotal.add(order.getShippingFee()));

    // 2) Reserve stock (decrease)
//    for (var it : req.getItems()) {
//      var res = inventoryClient.decrease(it.getProductId(), it.getQuantity());
//      if (res == null || !res.success()) {
//        throw new BusinessException("Failed to reserve stock for product " + it.getProductId());
//      }
//    }

    // 3) Persist order
    Order saved = orderRepository.save(order);

    // 4) Initiate payment async/sync
//    var pay = paymentClient.initiate(saved.getId(), saved.getTotal());
//    if (pay != null) {
//      saved.setPaymentReference(pay.reference());
//      saved.setPaymentStatus("SUCCESS".equalsIgnoreCase(pay.status()) ? PaymentStatus.SUCCESS : PaymentStatus.PENDING);
//      saved.setUpdatedAt(OffsetDateTime.now());
//      saved = orderRepository.save(saved);
//    }

    return toResponse(saved);
  }

  @Override
  public OrderResponse get(Long id) {
    return toResponse(orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found")));
  }

  @Override
  public Page<OrderResponse> list(Pageable pageable, Long userId, OrderStatus status) {
    Page<Order> page;
    if (userId != null) page = orderRepository.findByUserId(userId, pageable);
    else if (status != null) page = orderRepository.findByStatus(status, pageable);
    else page = orderRepository.findAll(pageable);
    return page.map(this::toResponse);
  }

  @Override
  @Transactional
  public void cancel(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
      throw new BusinessException("Cannot cancel after shipment");
    }
    order.setStatus(OrderStatus.CANCELLED);
    order.setUpdatedAt(OffsetDateTime.now());
    orderRepository.save(order);

    // release stock
    for (OrderItem it : order.getItems()) {
      inventoryClient.increase(it.getProductId(), it.getQuantity());
    }
  }

  @Override
  @Transactional
  public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest req) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    order.setStatus(req.getStatus());
    order.setUpdatedAt(OffsetDateTime.now());
    return toResponse(orderRepository.save(order));
  }

  @Override
  @Transactional
  public OrderResponse updatePaymentStatus(Long id, PaymentStatusUpdateRequest req) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    order.setPaymentStatus(req.getPaymentStatus());
    if (req.getPaymentReference() != null) order.setPaymentReference(req.getPaymentReference());
    order.setUpdatedAt(OffsetDateTime.now());
    return toResponse(orderRepository.save(order));
  }

  // mapper
  private OrderResponse toResponse(Order o) {
    List<OrderItemResponse> items = o.getItems().stream().map(it -> OrderItemResponse.builder()
            .id(it.getId()).productId(it.getProductId()).productName(it.getProductName())
            .unitPrice(it.getUnitPrice()).quantity(it.getQuantity()).lineTotal(it.getLineTotal())
            .build()).toList();

    return OrderResponse.builder()
            .id(o.getId())
            .userId(o.getUserId())
            .shippingAddressId(o.getShippingAddressId())
            .status(o.getStatus())
            .paymentStatus(o.getPaymentStatus())
            .paymentReference(o.getPaymentReference())
            .subtotal(o.getSubtotal())
            .shippingFee(o.getShippingFee())
            .total(o.getTotal())
            .createdAt(o.getCreatedAt())
            .updatedAt(o.getUpdatedAt())
            .items(items)
            .build();
  }
}
