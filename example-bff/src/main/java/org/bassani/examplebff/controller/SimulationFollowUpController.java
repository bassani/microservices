package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SimulationFollowUpService;
import org.bassani.examplemodellib.domain.request.SimulationFollowUpRequest;
import org.bassani.examplemodellib.domain.response.SimulationFollowUpResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/purchases/simulations-followup")
@RequiredArgsConstructor
@Validated
@Api(tags = { "Simulations Follow Up" })
public class SimulationFollowUpController {
    private final SimulationFollowUpService service;

    @PreAuthorize("hasRole('ROLE_SIMULATIONFOLLOWUP_POSTSIMULATIONFOLLOWUP')")
    @PostMapping
    @ApiOperation(value = "Acompanhamento das simulações de compra", authorizations = {@Authorization(value =
            "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Page<SimulationFollowUpResponse>> postStatusSimulation(
            @RequestBody(required = false) SimulationFollowUpRequest simulationFollowUpRequest,
            @PageableDefault(page = 0, size = 100, sort=
                    "registerDate", direction =
                    Sort.Direction.DESC) Pageable pageable) throws
            IOException {
        return ResponseEntity.ok(service.getAll(simulationFollowUpRequest, pageable));
    }
}
