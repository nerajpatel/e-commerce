// controller/TrackingController.java
package com.microshop.order.controller;

import com.microshop.order.dto.CreateTrackingRequest;
import com.microshop.order.dto.TrackingResponse;
import com.microshop.order.service.TrackingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}/tracking")
public class TrackingController {

  private final TrackingService trackingService;

  public TrackingController(TrackingService trackingService) {
    this.trackingService = trackingService;
  }

  @PostMapping
  public TrackingResponse add(@PathVariable Long orderId, @Valid @RequestBody CreateTrackingRequest req){
    return trackingService.add(orderId, req);
  }

  @GetMapping
  public List<TrackingResponse> list(@PathVariable Long orderId){
    return trackingService.list(orderId);
  }
}
