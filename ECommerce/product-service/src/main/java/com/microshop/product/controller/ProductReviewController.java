// ProductReviewController.java
package com.microshop.product.controller;

import com.microshop.product.dto.ProductReviewDto;
import com.microshop.product.service.ProductReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productId}/reviews")
public class ProductReviewController {

    private final ProductReviewService reviewService;

    public ProductReviewController(ProductReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ProductReviewDto> addReview(
            @PathVariable Long productId,
            @Valid @RequestBody ProductReviewDto dto) {
        return ResponseEntity.ok(reviewService.addReview(productId, dto));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ProductReviewDto> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ProductReviewDto dto) {
        return ResponseEntity.ok(reviewService.updateReview(productId, reviewId, dto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId) {
        reviewService.deleteReview(productId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductReviewDto>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviews(productId));
    }

    @GetMapping("/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getAverageRating(productId));
    }
}
