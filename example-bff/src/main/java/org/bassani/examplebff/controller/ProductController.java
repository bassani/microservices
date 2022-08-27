package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ProductService;
import org.bassani.examplemodellib.constraints.ValidId;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases/products")
@RequiredArgsConstructor
@Validated
@Api(tags = { "categorias", "subcategorias" })
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_PRODUCTCATEGORIES_GETALL')")
    @GetMapping("categories")
    @ApiOperation(value = "Obtém todas as cagorias", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<CategoryResponse>> getAll(CategoryRequest request,
                                                         @PageableDefault(sort= "name",
                                                                 direction = Sort.Direction.ASC)
                                                         Pageable page){
        return ResponseEntity.ok(productService.getAllCategories(request, page));
    }

    @PreAuthorize("hasRole('ROLE_PRODUCTSUBCATEGORIES_GETALL')")
    @GetMapping("subcategories")
    @ApiOperation(value = "Obtém todas as subcagorias", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<SubCategoryResponse>> getAllSubcategories(SubCategoryRequest request,
                                                                         @PageableDefault(sort= "name",
                                                                                 direction = Sort.Direction.ASC)
                                                                         Pageable page){
        return ResponseEntity.ok(productService.getAllSubcategories(request, page));
    }

    @PreAuthorize("hasRole('ROLE_PRODUCTSUBCATEGORIES_GETBYCATEGORYID')")
    @GetMapping("/categories/{id}/subcategories")
    @ApiOperation(value = "Obtém todos as subcategorias por id da categoria")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<SubCategoryResponse>> getAllSubCategoriesById(
            @ApiParam(value = "ID da categoria", required = true, example = "32") @ValidId @PathVariable Long id, @PageableDefault(size=1000) Pageable page) {
        return ResponseEntity.ok(productService.getAllSubcategoriesByCategory(id, page));
    }

}
