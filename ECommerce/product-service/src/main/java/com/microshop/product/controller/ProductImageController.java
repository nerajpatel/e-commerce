// ProductImageController.java
package com.microshop.product.controller;

import com.microshop.product.dto.ProductImageDto;
import com.microshop.product.service.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productId}/images")
public class ProductImageController {

    private final ProductImageService imageService;

    public ProductImageController(ProductImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ProductImageDto> addImage(
            @PathVariable Long productId,
            @Valid @RequestBody ProductImageDto dto) {
        return ResponseEntity.ok(imageService.addImage(productId, dto));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) {
        imageService.deleteImage(productId, imageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductImageDto>> getImages(@PathVariable Long productId) {
        return ResponseEntity.ok(imageService.getImages(productId));
    }
}
