// service/impl/ReturnServiceImpl.java
package com.microshop.order.service.impl;

import com.microshop.order.dto.CreateReturnRequest;
import com.microshop.order.dto.ReturnResponse;
import com.microshop.order.dto.UpdateReturnStatusRequest;
import com.microshop.order.exception.BusinessException;
import com.microshop.order.exception.ResourceNotFoundException;
import com.microshop.order.model.Order;
import com.microshop.order.model.OrderReturn;
import com.microshop.order.model.ReturnStatus;
import com.microshop.order.repository.OrderRepository;
import com.microshop.order.repository.OrderReturnRepository;
import com.microshop.order.service.ReturnService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ReturnServiceImpl implements ReturnService {

  private final OrderRepository orderRepository;
  private final OrderReturnRepository returnRepository;

  public ReturnServiceImpl(OrderRepository orderRepository, OrderReturnRepository returnRepository) {
    this.orderRepository = orderRepository;
    this.returnRepository = returnRepository;
  }

  @Override
  public ReturnResponse requestReturn(Long orderId, CreateReturnRequest req) {
    Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    if (order.getId() == null) throw new ResourceNotFoundException("Order not found");
    // guard: only delivered orders can be returned (example rule)
    if (order.getStatus() == null || !order.getStatus().name().equals("DELIVERED")) {
      throw new BusinessException("Return allowed only after delivery");
    }
    var ret = OrderReturn.builder()
            .order(order).status(ReturnStatus.REQUESTED).reason(req.getReason())
            .createdAt(OffsetDateTime.now()).updatedAt(OffsetDateTime.now()).build();
    return toResponse(returnRepository.save(ret));
  }

  @Override
  public ReturnResponse getByOrder(Long orderId) {
    var ret = returnRepository.findByOrderId(orderId).orElseThrow(() -> new ResourceNotFoundException("No return found"));
    return toResponse(ret);
  }

  @Override
  public ReturnResponse updateStatus(Long orderId, Long returnId, UpdateReturnStatusRequest req) {
    var ret = returnRepository.findById(returnId).orElseThrow(() -> new ResourceNotFoundException("Return not found"));
    if (!ret.getOrder().getId().equals(orderId)) throw new BusinessException("Return does not belong to order");
    ret.setStatus(req.getStatus());
    ret.setResolutionNote(req.getResolutionNote());
    ret.setUpdatedAt(OffsetDateTime.now());
    return toResponse(returnRepository.save(ret));
  }

  private ReturnResponse toResponse(OrderReturn r){
    return ReturnResponse.builder()
            .id(r.getId()).orderId(r.getOrder().getId())
            .status(r.getStatus()).reason(r.getReason())
            .resolutionNote(r.getResolutionNote())
            .createdAt(r.getCreatedAt()).updatedAt(r.getUpdatedAt()).build();
  }
}
