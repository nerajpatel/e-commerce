// controller/ReturnController.java
package com.microshop.order.controller;

import com.microshop.order.dto.CreateReturnRequest;
import com.microshop.order.dto.ReturnResponse;
import com.microshop.order.dto.UpdateReturnStatusRequest;
import com.microshop.order.service.ReturnService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{orderId}/return")
public class ReturnController {

  private final ReturnService returnService;

  public ReturnController(ReturnService returnService) {
    this.returnService = returnService;
  }

  @PostMapping
  public ReturnResponse requestReturn(@PathVariable Long orderId, @Valid @RequestBody CreateReturnRequest req){
    return returnService.requestReturn(orderId, req);
  }

  @GetMapping
  public ReturnResponse get(@PathVariable Long orderId){
    return returnService.getByOrder(orderId);
  }

  @PatchMapping("/{returnId}/status")
  public ReturnResponse updateStatus(@PathVariable Long orderId, @PathVariable Long returnId,
                                     @Valid @RequestBody UpdateReturnStatusRequest req){
    return returnService.updateStatus(orderId, returnId, req);
  }
}
