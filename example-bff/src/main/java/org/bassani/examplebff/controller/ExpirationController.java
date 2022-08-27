package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ExpirationService;
import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("purchases/expirations")
@RequiredArgsConstructor
@Api(tags = {"Expirations"})
public class ExpirationController {

    private final ExpirationService expirationService;

    @GetMapping
    @ApiOperation(value = "Lista todos os fluxos de expiração paginado", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Page<ExpirationDTO>> findAllPaginated(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(expirationService.getAllPaginated(pageable));
    }

}
