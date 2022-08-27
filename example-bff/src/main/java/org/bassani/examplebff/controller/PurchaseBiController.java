package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.PurchaseBiService;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Api(tags = {"Bi"})
@RequestMapping("/purchases/databi")
public class PurchaseBiController {

    private final PurchaseBiService purchaseBiService;

    @GetMapping("/{simulationId}/financial-data")
    @ApiOperation(value = "Busca os dados financeiros no MS-BI", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<List<PurchaseBiResponse>> getFinancialData(
            @ApiParam(value = "ID da simulação", required = true, example = "234587, 258963")
            @PathVariable Long simulationId) {
        return ResponseEntity.ok(purchaseBiService.getFinancialData(simulationId));
    }

    @GetMapping("/{simulationId}/chart")
    @ApiOperation(value = "Busca os dados financeiros para o grafico", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<List<PurchaseBiChartResponse>> getFinancialChartData(
            @ApiParam(value = "ID da simulação", required = true, example = "234587, 258963")
            @PathVariable Long simulationId) {
        return ResponseEntity.ok(purchaseBiService.getFinancialChartData(simulationId));
    }
}
