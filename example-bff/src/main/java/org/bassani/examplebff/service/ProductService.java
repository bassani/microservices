package org.bassani.examplebff.service;

import org.bassani.examplebff.client.ComposeClient;
import org.bassani.examplebff.client.ProductClient;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductClient productClient;
    private final ComposeClient compositionClient;

    public Page<CategoryResponse> getAllCategories(CategoryRequest request, Pageable page){
        Page<CategoryResponse> response = productClient.getAllCategories(request, page);
        return new PageImpl<>(response.getContent(), page, response.getTotalElements());
    }

    public Page<SubCategoryResponse> getAllSubcategories(SubCategoryRequest request, Pageable page){
        Page<SubCategoryResponse> response = productClient.getAllSubcategories(request, page);
        return new PageImpl<>(response.getContent(), page, response.getTotalElements());
    }

    public Page<SubCategoryResponse> getAllSubcategoriesByCategory(Long id, Pageable page){
        Page<SubCategoryResponse> response = compositionClient.getSubcategoriesByCategory(id, page);
        return new PageImpl<>(response.getContent(), page, response.getTotalElements());
    }

}
