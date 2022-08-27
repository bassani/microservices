package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SimulationPendingApprovalService;
import org.bassani.examplemodellib.domain.dto.SimulationPendingApprovalDTO;
import org.bassani.examplemodellib.domain.request.SimulationPendingApprovalRequest;
import org.bassani.examplemodellib.domain.response.SimulationPendingApprovalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Simulações Pendentes de Aprovação"})
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/purchases/simulations-pending-approval")
public class SimulationPendingApprovalController {

    private final SimulationPendingApprovalService service;

    @PreAuthorize("hasRole('ROLE_SIMULATIONPENDINGAPPROVAL_POSTFINDALLBYFILTER')")
    @PostMapping
    @ApiOperation(value = "Busca todas as simulações de compra pendentes de aprovação de acordo com os parametros",
            authorizations =
            {@Authorization(value =
                    "OAuth2")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<Page<SimulationPendingApprovalResponse>> findAllByFilter(@RequestBody @Valid SimulationPendingApprovalRequest simulationPendingApprovalRequest,
                                                                                   @PageableDefault(page = 0, size = 10, sort=
                                                                                   "registerDate", direction =
                                                                                   Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(simulationPendingApprovalRequest, pageable));
    }

    @PreAuthorize("hasRole('ROLE_SIMULATIONPENDINGAPPROVAL_GETALL')")
    @GetMapping
    @ApiOperation(value = "Busca todas as simulações de compra pendentes de aprovação",
            authorizations =
                    {@Authorization(value =
                            "OAuth2")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<List<SimulationPendingApprovalDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


}
