package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.DistributionCenterService;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.response.DistributionCenterResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Api(tags = { "Distribution Center" })
@RequiredArgsConstructor
@RestController
@RequestMapping("/purchases/distributionCenters")
public class DistributionCenterController {

	private final DistributionCenterService service;

    @PreAuthorize("hasRole('ROLE_DISTRIBUTIONCENTER_GETALL')")
	@GetMapping
	@ApiOperation(value = "Obtém todos os Centros de Distribuição", authorizations = {
			@Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 500, message = "Erro desconhecido") })
	public ResponseEntity<Page<DistributionCenterResponse>> getAll(
			DistributionCenterRequest request, Pageable pageable) {
		return ResponseEntity.ok(service.getAllDistributionCenters(request, pageable));
	}

    @PreAuthorize("hasRole('ROLE_DISTRIBUTIONCENTER_GETBYID')")
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtém Centros de Distribuição por ID", authorizations = {
			@Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 500, message = "Erro desconhecido") })
	public ResponseEntity<DistributionCenterResponse> getById(
			@PathVariable Long id) {
		return ResponseEntity.ok(service.getDistributionCenterById(id));
	}

}