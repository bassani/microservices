package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceSupplierOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierSubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.ProductSupplierResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-source-supplier", url = "${ms-example-source-supplier.url}", configuration =
        PurchaseSimulatorSourceSupplierOAuthFeignConfig.class)
public interface SourceSupplierClient {

	@PostMapping("/products")
	Page<ProductSupplierResponse> getAllProductBySupplier(@RequestBody ProductSupplierRequest request, @RequestParam String query, @SpringQueryMap Pageable pageable);

    @GetMapping("/products/categories")
    Page<CategoryResponse> getAllCategories(@SpringQueryMap ProductSupplierCategoryRequest request, Pageable page);

    @GetMapping("/products/subcategories")
    Page<SubCategoryResponse> getAllSubcategories(@SpringQueryMap ProductSupplierSubCategoryRequest request, Pageable page);
}
