// service/impl/TrackingServiceImpl.java
package com.microshop.order.service.impl;

import com.microshop.order.dto.CreateTrackingRequest;
import com.microshop.order.dto.TrackingResponse;
import com.microshop.order.exception.ResourceNotFoundException;
import com.microshop.order.model.Order;
import com.microshop.order.model.OrderTracking;
import com.microshop.order.repository.OrderRepository;
import com.microshop.order.repository.OrderTrackingRepository;
import com.microshop.order.service.TrackingService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

  private final OrderRepository orderRepository;
  private final OrderTrackingRepository trackingRepository;

  public TrackingServiceImpl(OrderRepository orderRepository, OrderTrackingRepository trackingRepository) {
    this.orderRepository = orderRepository;
    this.trackingRepository = trackingRepository;
  }

  @Override
  public TrackingResponse add(Long orderId, CreateTrackingRequest req) {
    Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    OrderTracking tr = trackingRepository.save(OrderTracking.builder()
            .order(order).courier(req.getCourier()).trackingNumber(req.getTrackingNumber())
            .status(req.getStatus()).note(req.getNote()).createdAt(OffsetDateTime.now()).build());
    return toResponse(tr);
  }

  @Override
  public List<TrackingResponse> list(Long orderId) {
    return trackingRepository.findByOrderId(orderId).stream().map(this::toResponse).toList();
  }

  private TrackingResponse toResponse(OrderTracking t){
    return TrackingResponse.builder()
            .id(t.getId()).orderId(t.getOrder().getId())
            .courier(t.getCourier()).trackingNumber(t.getTrackingNumber())
            .status(t.getStatus()).note(t.getNote()).createdAt(t.getCreatedAt())
            .build();
  }
}
