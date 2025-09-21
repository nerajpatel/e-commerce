package com.microshop.product.service;

import com.microshop.product.dto.ProductRequest;
import com.microshop.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, Pageable pageable);

    List<ProductResponse> getProductsBySeller(Long sellerId);
}
