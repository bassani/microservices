package org.bassani.examplebff.controller;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.service.SimulationCashCycleService;
import org.bassani.examplebff.service.SimulationOrderSummaryService;
import org.bassani.examplebff.service.SimulationService;
import org.bassani.examplebff.service.SimulationTemplateService;
import org.bassani.examplemodellib.constraints.ValidId;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.request.SimulationOrderRequest;
import org.bassani.examplemodellib.domain.response.SimulationOrderSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationProductResponseWrapper;
import org.bassani.examplemodellib.domain.response.SimulationSummaryCashCycleResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryDCResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationTemplateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Api(tags = {"Simulation"})
@RequestMapping("/purchases/simulations")
public class SimulationController {

    private final SimulationService service;
    private final SimulationTemplateService simulationTemplateService;
    private final SimulationOrderSummaryService simulationOrderSummaryService;
    private final SimulationCashCycleService simulationCashCycleService;
    private final CoreClient coreClient;

    @PreAuthorize("hasRole('ROLE_SIMULATION_SIMULATE')")
    @PostMapping
    @ApiOperation(value = "Salvar nova simulação de compra", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 201, message = "Simulação criada"),
            @ApiResponse(code = 400, message = "Requisição mal formada"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<SimulationDTO> simulate(@Valid @RequestBody SimulationDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveParameters(request));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_GETPARAMETERS')")
    @GetMapping("/{simulationId}")
    @ApiOperation(value = "Obtém os parâmetros da simulação", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID de simulação inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "ID de simulação não encontrado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<SimulationDTO> getParameters(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @ValidId @PathVariable Long simulationId) {
        return ResponseEntity.ok(service.getSimulationParameters(simulationId));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_GETSUMMARY')")
    @GetMapping("/{simulationId}/summary")
    @ApiOperation(value = "Obtém o resumo da simulação", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID de simulação inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "ID de simulação não encontrado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public List<SimulationSummaryResponse> getSummary(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @ValidId @PathVariable Long simulationId) {
        return service.getSimulationSummary(simulationId);
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_GETSUMMARYDC')")
    @GetMapping("/{simulationId}/summary-dc")
    @ApiOperation(value = "Obtém o resumo da simulação por CD/Fabricante", authorizations =
            { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID de simulação inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "ID de simulação não encontrado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public List<SimulationSummaryDCResponse> getSummaryDC(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @ValidId @PathVariable Long simulationId) {
        return service.getSimulationSummaryDC(simulationId);
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_TEMPLATE')")
    @GetMapping("/{simulationId}/template")
    @ApiOperation(value = "Retorna o Template da Simulação através do id da Simulação", authorizations =
            { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<SimulationTemplateResponse> getSimulationTeplate(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @PathVariable Long simulationId) {
        return ResponseEntity.ok(simulationTemplateService.getSimulationTemplate(simulationId));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_ORDERSUMMARY')")
    @GetMapping("/{simulationId}/order-summary")
    @ApiOperation(value = "Retorna o resumo do pedido da simulação de compra por id da simulação", authorizations =
            { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<SimulationOrderSummaryResponse> getSimulationOrderSummary(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @PathVariable Long simulationId) {
        return ResponseEntity.ok(simulationOrderSummaryService.getSimulationOrderSummary(simulationId));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_TEMPLATE')")
    @GetMapping("/{simulationId}/cash-cycle")
    @ApiOperation(value = "Retorna o calculo de ciclo de caixa da simulação de compra por id da simulação", authorizations =
            { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<SimulationSummaryCashCycleResponse> getSimulationCashCycle(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @PathVariable Long simulationId) {
        return ResponseEntity.ok(simulationCashCycleService.getSimulationCashCycle(simulationId));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_TEMPLATE')")
    @GetMapping("orders/{id}")
    @ApiOperation(value = "Busca simulações por id com detalhes prontos para emissão do pedido de compras.",
            authorizations = {
                    @Authorization(value = "OAuth2")
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<SimulationProductResponseWrapper> simulationOrders(@PathVariable Long id) {
        return ResponseEntity.ok(coreClient.getSimulationOrder(id));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATION_CREATE_ORDER')")
    @PostMapping("orders/{simulationId}")
    @ApiOperation(value = "Emite os pedidos de compras para uma simulação.",
            authorizations = {
                    @Authorization(value = "OAuth2")
            })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<SimulationProductResponseWrapper> simulationOrders(@PathVariable Long simulationId,
                                                                             @RequestBody SimulationOrderRequest request) {
        return ResponseEntity.ok(coreClient.createSimulationOrder(simulationId, request));
    }
}