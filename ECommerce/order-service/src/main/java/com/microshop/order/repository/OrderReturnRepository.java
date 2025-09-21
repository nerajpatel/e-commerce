package com.microshop.order.repository;

import com.microshop.order.model.OrderReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {
  Optional<OrderReturn> findByOrderId(Long orderId);
}
