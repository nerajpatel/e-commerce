// client/ProductClient.java
package com.microshop.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="product-service", path="/products")
@FeignClient(name = "product-service")
public interface ProductClient {
  @GetMapping("/productmgmt/rest/products/{id}")
  ProductDTO getProduct(@PathVariable("id") Long id);

  record ProductDTO(Long id, String name, java.math.BigDecimal price) {}
}
