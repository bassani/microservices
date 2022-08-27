package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.MenuItemService;
import org.bassani.examplemodellib.constraints.ValidId;
import org.bassani.examplemodellib.domain.response.MenuItemResponse;
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

@Deprecated
@RestController
@RequestMapping("/purchases/menus")
@RequiredArgsConstructor
@Validated
@Api(tags = {"MenuItem"})
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PreAuthorize("hasRole('ROLE_MENUITEM_GETALL')")
    @GetMapping("/{menuId}/items")
    @ApiOperation(value = "Obtém todos os items do menu", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID do menu inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "ID do menu não encontrado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
    public ResponseEntity<List<MenuItemResponse>> getAll(
            @ApiParam(value = "ID do menu", required = true) @ValidId @PathVariable Integer menuId) {
        return ResponseEntity.ok(menuItemService.getAllMenuItems(menuId));
    }

    @PreAuthorize("hasRole('ROLE_MENUITEM_GETBYID')")
    @GetMapping("/{menuId}/items/{menuItemId}")
    @ApiOperation(value = "Obtém o item do menu", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID do menu ou do item do menu inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "ID do menu ou do item do menu não encontrado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
    public ResponseEntity<MenuItemResponse> getById(
            @ApiParam(value = "ID do menu", required = true) @ValidId @PathVariable Integer menuId,
            @ApiParam(value = "ID do item do menu", required = true) @ValidId @PathVariable Integer menuItemId) {
        return ResponseEntity.ok(menuItemService.getMenuItem(menuId, menuItemId));
    }
}