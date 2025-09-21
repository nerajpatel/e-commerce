package com.microshop.product.service;

import com.microshop.product.dto.CategoryRequest;
import com.microshop.product.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);
}
