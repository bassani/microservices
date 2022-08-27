package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.CalculationBasisService;
import org.bassani.examplemodellib.domain.response.CalculationBasisOptionGroupResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchases/calculationBasis")
@RequiredArgsConstructor
@Validated
@Api(tags = {"CalculationBasis"})
public class CalculationBasisController {

    private final CalculationBasisService service;

    @PreAuthorize("hasRole('ROLE_CALCULATIONBASIS_GETALL')")
    @GetMapping
    @ApiOperation(value = "Obtém todas as bases de cálculo", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
    public ResponseEntity<List<CalculationBasisOptionGroupResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
}