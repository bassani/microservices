package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SimulationStatusService;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
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

@Api(tags = {"Status"})
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/purchases/status")
public class StatusController {

    private final SimulationStatusService service;

    @PreAuthorize("hasRole('ROLE_STATUS_GETALL')")
    @GetMapping
    @ApiOperation(value = "Busca todos os Status", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<List<SimulationStatusResponse>> getAll() {
        List<SimulationStatusResponse> response = service.getAllStatus();
        return ResponseEntity.ok(response);
    }
}
