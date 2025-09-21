// service/TrackingService.java
package com.microshop.order.service;

import com.microshop.order.dto.CreateTrackingRequest;
import com.microshop.order.dto.TrackingResponse;

import java.util.List;

public interface TrackingService {
  TrackingResponse add(Long orderId, CreateTrackingRequest req);
  List<TrackingResponse> list(Long orderId);
}
