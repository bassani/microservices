package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SimulationStatusService;
import org.bassani.examplemodellib.constraints.ValidId;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = {"Simulation Status"})
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/purchases/simulations")
public class SimulationStatusController {

    private final SimulationStatusService service;

    @PreAuthorize("hasRole('ROLE_SIMULATIONSTATUS_GETSIMULATIONSBYSTATUS')")
    @GetMapping("/{id}/status")
    @ApiOperation(value = "Buscar simulação de compra por id", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<SimulationStatusResponse> getStatusSimulation(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @ValidId @PathVariable Long id) {
        SimulationStatusResponse response = service.getSimulationStatus(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SIMULATIONSTATUS_GETSTATUSSIMULATION')")
    @GetMapping("/status")
    @ApiOperation(value = "Busca simulações com o status especificado", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<List<Long>> getSimulationsByStatus(
            @ApiParam(value = "Status", example = "DRAFT") @RequestParam(required = false) SimulationStatusEnum status,
            @ApiParam(value = "Tempo decorrido do início da simulação", example = "10") @RequestParam(required = false)
                    Long minutesElapsed) {
        List<Long> response = service.getSimulationIdsByStatus(status, minutesElapsed);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SIMULATIONSTATUS_UPDATESIMULATIONSSTATUS')")
    @PutMapping("/status")
    @ApiOperation(value = "Atualiza status de simulações", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Void> updateSimulationsStatus(
            @ApiParam(value = "Status", example = "PROCESSING") @RequestParam(required = false)
                    SimulationStatusEnum status,
            @ApiParam(value = "IDs de simulações", required = true, example = "[1, 2, 3]") @RequestBody
            @NotEmpty List<Long> simulationIds) {
        service.updateSimulationsStatus(status, simulationIds);
        return ResponseEntity.ok().build();
    }
}
