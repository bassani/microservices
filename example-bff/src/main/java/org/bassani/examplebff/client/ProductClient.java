package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.ProductOAuth2FeignClient;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "ms-product-v2", url = "${ms-product-v2.url}", configuration = ProductOAuth2FeignClient.class)
public interface ProductClient {

    @GetMapping("/categories")
    Page<CategoryResponse> getAllCategories(@SpringQueryMap CategoryRequest request, Pageable page);

    @GetMapping("/subcategories")
    Page<SubCategoryResponse> getAllSubcategories(@SpringQueryMap SubCategoryRequest request, Pageable page);

}
