// ProductReviewService.java
package com.microshop.product.service;

import com.microshop.product.dto.ProductReviewDto;

import java.util.List;

public interface ProductReviewService {
    ProductReviewDto addReview(Long productId, ProductReviewDto dto);
    ProductReviewDto updateReview(Long productId, Long reviewId, ProductReviewDto dto);
    void deleteReview(Long productId, Long reviewId);
    List<ProductReviewDto> getReviews(Long productId);
    Double getAverageRating(Long productId);
}
