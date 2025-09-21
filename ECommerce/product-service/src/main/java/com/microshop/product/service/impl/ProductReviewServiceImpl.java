// ProductReviewServiceImpl.java
package com.microshop.product.service.impl;

import com.microshop.product.dto.ProductReviewDto;
import com.microshop.product.model.Product;
import com.microshop.product.model.Review;
import com.microshop.product.repository.ProductRepository;
import com.microshop.product.repository.ProductReviewRepository;
import com.microshop.product.service.ProductReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductRepository productRepository;
    private final ProductReviewRepository reviewRepository;

    public ProductReviewServiceImpl(ProductRepository productRepository, ProductReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ProductReviewDto addReview(Long productId, ProductReviewDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Review review = Review.builder()
                .product(product)
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
        dto.setId(review.getId());
        return dto;
    }

    @Override
    public ProductReviewDto updateReview(Long productId, Long reviewId, ProductReviewDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getProduct().getId().equals(productId)) {
            throw new RuntimeException("Review does not belong to product");
        }

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);

        dto.setId(review.getId());
        return dto;
    }

    @Override
    public void deleteReview(Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getProduct().getId().equals(productId)) {
            throw new RuntimeException("Review does not belong to product");
        }
        reviewRepository.delete(review);
    }

    @Override
    public List<ProductReviewDto> getReviews(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(r -> ProductReviewDto.builder()
                        .id(r.getId())
                        .userId(r.getUserId())
                        .rating(r.getRating())
                        .comment(r.getComment())
                        .productId(r.getProduct().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }
}
