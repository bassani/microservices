package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.MenuService;
import org.bassani.examplemodellib.constraints.ValidId;
import org.bassani.examplemodellib.domain.response.MenuResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchases/menus")
@RequiredArgsConstructor
@Validated
@Api(tags = { "Menu" })
public class MenuController {

    private final MenuService menuService;

    @PreAuthorize("hasRole('ROLE_MENU_GETALL')")
    @GetMapping
    @ApiOperation(value = "Obtém todos os menus", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<List<MenuResponse>> getAll() {
        return ResponseEntity.ok(menuService.getAll());
    }

    @PreAuthorize("hasRole('ROLE_MENU_GETBYID')")
    @GetMapping("/{menuId}")
    @ApiOperation(value = "Obtém um menu", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID do menu inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "ID do menu não encontrado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<MenuResponse> getById(
            @ApiParam(value = "ID do menu", required = true, example = "10") @ValidId @PathVariable Integer menuId) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
    }
}
