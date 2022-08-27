package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SimulationReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequiredArgsConstructor
@Api(tags = {"Download"})
@RequestMapping("/purchases/simulations")
public class SimulationReportController {

    private final SimulationReportService simulationExportService;

    @PreAuthorize("hasRole('ROLE_SIMULATIONREPORT_DOWNLOAD')")
    @GetMapping("/{id}/download")
    @ApiOperation(value = "Obtém arquivo Excel com os dados da simulação",
            authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<Resource> download(@Positive @PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(simulationExportService.getSpreadsheet(id));
    }
}
