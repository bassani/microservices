package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ProductSupplierService;
import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierSubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.ProductSupplierResponse;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@Api(tags = {"ProductSupplier"})
@RequestMapping("/purchases/supplier-products")
public class ProductSupplierController {

	private final ProductSupplierService service;

    @PreAuthorize("hasRole('ROLE_PRODUCTSUPPLIER_GETALL')")
	@PostMapping
	@ApiOperation(value = "Lista produtos por fabricante", authorizations = { @Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 400, message = "Solicitação ruim"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 500, message = "Erro interno") })
	public ResponseEntity<Page<ProductSupplierResponse>> getAll(@RequestBody @Valid ProductSupplierRequest request,
			@ApiParam(name = "query", value = "Código ou descrição do produto", required = true) @RequestParam(name = "query", required = true) String query,
			@PageableDefault Pageable pageable) {
    	return ResponseEntity.ok(service.getAllProductBySupplier(request, query, pageable));
	}

    @PreAuthorize("hasRole('ROLE_PRODUCTSUPPLIERCATEGORIES_GETALL')")
    @GetMapping("categories")
    @ApiOperation(value = "Obtém todas as categorias de produtos dos fornecedores", authorizations =
            { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<CategoryResponse>> getAll(@Validated ProductSupplierCategoryRequest request,
                                                         @PageableDefault Pageable page){
        return ResponseEntity.ok(service.getAllCategories(request, page));
    }

    @PreAuthorize("hasRole('ROLE_PRODUCTSUPPLIERSUBCATEGORIES_GETALL')")
    @GetMapping("subcategories")
    @ApiOperation(value = "Obtém todas as subcategorias de produtos dos fornecedores", authorizations =
            { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<SubCategoryResponse>> getAllSubcategories(@Validated ProductSupplierSubCategoryRequest request,
                                                                         @PageableDefault Pageable page){
        return ResponseEntity.ok(service.getAllSubcategories(request, page));
    }
}
