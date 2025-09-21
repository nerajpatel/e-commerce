// ProductReviewRepository.java
package com.microshop.product.repository;

import com.microshop.product.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
}
