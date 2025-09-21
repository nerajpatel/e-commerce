// controller/OrderItemController.java
package com.microshop.order.controller;

import com.microshop.order.dto.OrderItemResponse;
import com.microshop.order.exception.ResourceNotFoundException;
import com.microshop.order.model.OrderItem;
import com.microshop.order.repository.OrderItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}/items")
public class OrderItemController {

  private final OrderItemRepository itemRepository;

  public OrderItemController(OrderItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @GetMapping
  public List<OrderItemResponse> list(@PathVariable Long orderId){
    return itemRepository.findByOrderId(orderId).stream().map(it -> OrderItemResponse.builder()
            .id(it.getId()).productId(it.getProductId()).productName(it.getProductName())
            .unitPrice(it.getUnitPrice()).quantity(it.getQuantity()).lineTotal(it.getLineTotal()).build()
    ).toList();
  }

  @GetMapping("/{itemId}")
  public OrderItemResponse one(@PathVariable Long orderId, @PathVariable Long itemId){
    OrderItem it = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    if(!it.getOrder().getId().equals(orderId)) throw new ResourceNotFoundException("Item not in order");
    return OrderItemResponse.builder()
            .id(it.getId()).productId(it.getProductId()).productName(it.getProductName())
            .unitPrice(it.getUnitPrice()).quantity(it.getQuantity()).lineTotal(it.getLineTotal()).build();
  }
}
