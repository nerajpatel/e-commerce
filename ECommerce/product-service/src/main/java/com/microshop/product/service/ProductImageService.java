// ProductImageService.java
package com.microshop.product.service;

import com.microshop.product.dto.ProductImageDto;

import java.util.List;

public interface ProductImageService {
    ProductImageDto addImage(Long productId, ProductImageDto dto);
    void deleteImage(Long productId, Long imageId);
    List<ProductImageDto> getImages(Long productId);
}
