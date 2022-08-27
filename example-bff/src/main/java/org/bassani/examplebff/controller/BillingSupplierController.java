package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.BillingSupplierService;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/purchases/supplier/billing")
@RestController
@Validated
@Api(tags = { "ManufacturerBff" })
public class BillingSupplierController {

	private final BillingSupplierService billingSupplierService;

	@GetMapping@PreAuthorize("hasRole('ROLE_BILLING_SUPPLIER_GETALL')")
	@ApiOperation(value = "Lista todos os fabricantes para compra", authorizations = { @Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Erro interno") })
	public ResponseEntity<Object> getAllBillingSupplierToPurchase(@PageableDefault(page = 0, size = 25, sort= "name", direction = Sort.Direction.ASC) Pageable pageable,
										 ManufacturerRequest manufacturer) throws IOException {
		return ResponseEntity.ok(billingSupplierService.getAllBillingSupplier(manufacturer, pageable));
	}
}
