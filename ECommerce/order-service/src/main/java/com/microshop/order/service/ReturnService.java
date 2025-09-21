// service/ReturnService.java
package com.microshop.order.service;

import com.microshop.order.dto.CreateReturnRequest;
import com.microshop.order.dto.ReturnResponse;
import com.microshop.order.dto.UpdateReturnStatusRequest;

public interface ReturnService {
  ReturnResponse requestReturn(Long orderId, CreateReturnRequest req);
  ReturnResponse getByOrder(Long orderId);
  ReturnResponse updateStatus(Long orderId, Long returnId, UpdateReturnStatusRequest req);
}
