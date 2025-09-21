package com.microshop.order.repository;

import com.microshop.order.model.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long> {
  List<OrderTracking> findByOrderId(Long orderId);
}
