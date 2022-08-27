package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.OrderTypeService;
import org.bassani.examplemodellib.domain.request.param.OrderTypeRequestParams;
import org.bassani.examplemodellib.domain.response.OrderTypeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases/typesorder")
@RequiredArgsConstructor
@Api(tags = {"OrderType"})
public class OrderTypeController {

    private final OrderTypeService orderTypeService;

    @PreAuthorize("hasRole('ROLE_ORDERTYPE_GETALL')")
    @GetMapping
    @ApiOperation(value = "Obtém página com o tipo de pedido", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
    public ResponseEntity<Page<OrderTypeResponse>> getAll(OrderTypeRequestParams request,
                                                                    @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(orderTypeService.getOrderTypesPage(request, pageable));
    }
}
