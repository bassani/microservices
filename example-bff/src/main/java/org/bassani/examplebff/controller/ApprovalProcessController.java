package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ApprovalProcessService;
import org.bassani.examplemodellib.domain.request.SimulationApprovalCompleteRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceWithVariablesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Approval Process"})
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/purchases/approval")
public class ApprovalProcessController {

    private final ApprovalProcessService approvalProcessService;

    @PreAuthorize("hasRole('ROLE_APPROVAL_STARTAPPROVALPROCESS')")
    @PostMapping("/{simulationId}/start")
    @ApiOperation(value = "Inicia processo de aprovação para uma simulação", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    public ProcessInstanceWithVariablesDto startApprovalProcessInstance(
            @ApiParam(value = "ID da simulação", required = true, example = "10") @PathVariable Long
            simulationId) {
        return approvalProcessService.startApprovalProcessInstance(simulationId);
    }

    @PreAuthorize("hasRole('ROLE_APPROVAL_COMPLETEAPPROVALTASK')")
    @PostMapping("/{simulationId}/complete")
    @ApiOperation(value = "Completa a tarefa de aprovação para uma simulação", authorizations =
            { @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Boolean> completeApprovalTask(
            @ApiParam(value = "Id da simulação para aprovação", required = true, example = "1234") @PathVariable Long simulationId,
            @RequestBody SimulationApprovalCompleteRequest request) {
        return ResponseEntity.ok(approvalProcessService.completeApprovalTask(simulationId, request));
    }
}
