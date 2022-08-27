package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SimulationTypesService;
import org.bassani.examplemodellib.domain.request.SimulationTypesRequestParams;
import org.bassani.examplemodellib.domain.response.SimulationTypesResponse;
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
@RequestMapping("/purchases/simulationTypes")
@RequiredArgsConstructor
@Validated
@Api(tags = { "Simulations Types" })
public class SimulationTypesController {

    private final SimulationTypesService simulationTypesService;

    @PreAuthorize("hasRole('ROLE_SIMULATIONTYPES_GETALL')")
    @GetMapping
    @ApiOperation(value = "Obtém todos os tipos de simulação", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<List<SimulationTypesResponse>> getAll(SimulationTypesRequestParams request){
        return ResponseEntity.ok(simulationTypesService.getAll(request));
    }

}
