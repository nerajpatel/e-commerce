// ProductImageDto.java
package com.microshop.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDto {
    private Long id;

    @NotBlank(message = "Image URL is required")
    private String url;
}
