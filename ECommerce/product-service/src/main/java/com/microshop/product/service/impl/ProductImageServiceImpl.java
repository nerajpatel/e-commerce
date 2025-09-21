// ProductImageServiceImpl.java
package com.microshop.product.service.impl;

import com.microshop.product.dto.ProductImageDto;
import com.microshop.product.model.Product;
import com.microshop.product.model.ProductImage;
import com.microshop.product.repository.ProductImageRepository;
import com.microshop.product.repository.ProductRepository;
import com.microshop.product.service.ProductImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;

    public ProductImageServiceImpl(ProductRepository productRepository, ProductImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public ProductImageDto addImage(Long productId, ProductImageDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductImage image = ProductImage.builder()
                .url(dto.getUrl())
                .product(product)
                .build();

        imageRepository.save(image);
        dto.setId(image.getId());
        return dto;
    }

    @Override
    public void deleteImage(Long productId, Long imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (!image.getProduct().getId().equals(productId)) {
            throw new RuntimeException("Image does not belong to product");
        }
        imageRepository.delete(image);
    }

    @Override
    public List<ProductImageDto> getImages(Long productId) {
        return imageRepository.findByProductId(productId).stream()
                .map(img -> new ProductImageDto(img.getId(), img.getUrl()))
                .collect(Collectors.toList());
    }
}
