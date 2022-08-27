package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ManufacturerService;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/purchases/manufacturers")
@RestController
@Validated
@Api(tags = { "ManufacturerBff" })
public class ManufacturerController {

	private final ManufacturerService manufacturerService;

    @PreAuthorize("hasRole('ROLE_MANUFACTURER_GETALL')")
	@GetMapping
	@ApiOperation(value = "Lista todos os fabricantes", authorizations = { @Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Erro interno") })
	public ResponseEntity<Object> getAll(@PageableDefault(page = 0, size = 25, sort= "id", direction = Sort.Direction.ASC) Pageable pageable,
										 ManufacturerRequest manufacturer) throws IOException {
		return ResponseEntity.ok(manufacturerService.getAll(manufacturer, pageable));
	}

    @PreAuthorize("hasRole('ROLE_MANUFACTURER_GETBYID')")
	@GetMapping("/{manufactorerId}")
	@ApiOperation(value = "Traz um fabricante", authorizations = { @Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Erro interno") })
	public ResponseEntity<Object> getById(
			@ApiParam(value = "ID do fabricante", required = true, example = "10") @Positive @PathVariable Integer manufactorerId) {
		return ResponseEntity.ok(manufacturerService.getManufacturer(manufactorerId));
	}

    @PreAuthorize("hasRole('ROLE_MANUFACTURER_GETALL')")
    @GetMapping("/parents")
    @ApiOperation(value = "Lista todos os fabricantes 'pai'", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Object> getAllParentsManufacturers(@PageableDefault(page = 0, size = 25, sort = "name",
            direction =
            Sort.Direction.ASC) Pageable pageable,
            ManufacturerRequest manufacturer) throws IOException {
        return ResponseEntity.ok(manufacturerService.getAllParentsManufacturer(manufacturer, pageable));
    }
}
