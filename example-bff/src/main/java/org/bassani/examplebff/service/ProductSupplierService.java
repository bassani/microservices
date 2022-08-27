package org.bassani.examplebff.service;

import org.bassani.examplebff.client.SourceSupplierClient;
import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierSubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.ProductSupplierResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductSupplierService {

	private final SourceSupplierClient sourceSupplierClient;

	public Page<ProductSupplierResponse> getAllProductBySupplier(ProductSupplierRequest request, String query, Pageable pageable) {
		Page<ProductSupplierResponse> page = sourceSupplierClient.getAllProductBySupplier(request, query, pageable);
		return new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
	}

    public Page<CategoryResponse> getAllCategories(ProductSupplierCategoryRequest request, Pageable page) {
        Page<CategoryResponse> response = sourceSupplierClient.getAllCategories(request, page);
        return new PageImpl<>(response.getContent(), page, response.getTotalElements());
    }

    public Page<SubCategoryResponse> getAllSubcategories(ProductSupplierSubCategoryRequest request, Pageable page) {
        Page<SubCategoryResponse> response = sourceSupplierClient.getAllSubcategories(request, page);
        return new PageImpl<>(response.getContent(), page, response.getTotalElements());
    }
}
