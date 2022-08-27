package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ExpirationParameterService;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterRequest;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("purchases/expiration-parameters")
@RequiredArgsConstructor
@Validated
@Api(tags = {"Expiration Parameters"})
public class ExpirationParameterController {

    private final ExpirationParameterService expirationParameterService;

    @GetMapping
    @ApiOperation(value = "Lista todos os parametros de expiração paginado", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Page<ExpirationParameterResponse>> findAllPaginated(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 6) Pageable pageable) {

        return ResponseEntity.ok(expirationParameterService.getAllPaginated(pageable));
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Lista o parametro de expiração pelo id", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<ExpirationParameterResponse> findById(
            @ApiParam(value = "ID do parametro de expiração", required = true, example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(expirationParameterService.findById(id));
    }

    @PostMapping
    @ApiOperation(value = "Cria um parametro de expiração", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<ExpirationParameterResponse> save(@RequestBody @Valid ExpirationParameterRequest request) {

        var response = expirationParameterService.save(request);
        return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri())
                .body(response);
    }

    @GetMapping(params = { "latest" })
    @ApiOperation(value = "Lista os últimos parametros de expiração cadastrados", authorizations = {
            @Authorization(value = "OAuth2")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<List<ExpirationParameterResponse>> findResume(@RequestParam("latest") Boolean latest) {

        return ResponseEntity.ok(expirationParameterService.findLatest());
    }
}
