package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.SourceOperatorService;
import org.bassani.examplemodellib.domain.request.OperatorRequest;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/purchases/operators")
@RequiredArgsConstructor
@Validated
@Api(tags = { "Source Operators" })
public class SourceOperatorController {

    private final SourceOperatorService sourceOperatorService;

    @PreAuthorize("hasRole('ROLE_SOURCEOPERATORS_GETALL')")
    @GetMapping
    @ApiOperation(value = "Lista todos os operadores", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<Page<OperatorResponse>> getAll(
    OperatorRequest operatorRequest,
    Pageable pageable) throws IOException {
        return ResponseEntity.ok(sourceOperatorService.getAll(operatorRequest, pageable));
    }
}

