// ProductReviewDto.java
package com.microshop.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewDto {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;

    @NotBlank(message = "Review comment cannot be empty")
    private String comment;

    private Long productId;
}
