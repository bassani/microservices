package org.bassani.exampleorder.controller;

import org.bassani.examplemodellib.domain.request.OrderParamRequest;
import org.bassani.examplemodellib.domain.request.OrderRequest;
import org.bassani.examplemodellib.domain.response.OrderParamResponse;
import org.bassani.exampleorder.service.OrderService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/purchases/order")
@Validated
@RequiredArgsConstructor
@Api(tags = {"Pedidos de Compra"})
public class OrderController {
    private final OrderService service;

    @PostMapping("/find")
    @ApiOperation(value = "Busca todos os pedidos de compra baseado nos parametros do request",
            authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<Page<OrderParamResponse>> findAllByFilter(@RequestBody @Valid OrderParamRequest orderRequest,
                                                                    @PageableDefault(page = 0, size = 10, sort=
                                                                                           "sentDate", direction =
                                                                                           Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(orderRequest, pageable));
    }

    @PostMapping
    @ApiOperation(value = "Salva um novo pedido de compra",
            authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno") })
    public ResponseEntity<OrderParamResponse> saveOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(service.saveOrder(orderRequest));
    }
}