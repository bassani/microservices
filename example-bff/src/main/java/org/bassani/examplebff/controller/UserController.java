package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.UserService;
import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.PositionResponse;
import org.bassani.examplemodellib.domain.response.UserResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchases/users")
@RequiredArgsConstructor
@Api(tags = {"Usuários"})
public class UserController {

    private final UserService service;

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_GET')")
    @GetMapping
    @ApiOperation(value = "Obtém todos os usuários", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<Page<UserResponse>> getAll(
            @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.getAllUsers(page, search));
    }

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_GET_POSITIONS')")
    @GetMapping("/positions")
    @ApiOperation(value = "Obtém todos os cargos possiveis para os usuários",
            authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<List<PositionResponse>> getAllPositions() {
        return ResponseEntity.ok(service.getAllPositions());
    }

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_GET_USERPOSITION')")
    @GetMapping("/{id}/position")
    @ApiOperation(value = "Obtém o cargo do usuário", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<PositionResponse> getUserPosition(
            @ApiParam(value = "Id usuário no KeyCloak", required = true, example = "9669b7c7-4fea-480c-84db-11537e0110b2")
            @PathVariable("id") String id) {
        return ResponseEntity.ok(service.getUserPosition(id));

    }

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_SAVE')")
    @PostMapping
    @ApiOperation(value = "Salvar novo usuário", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")})
    public ResponseEntity<Void> save(@RequestBody UserRequest request) {
        service.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_UPDATE')")
    @PutMapping
    @ApiOperation(value = "Alterar usuário existente", authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")})
    public ResponseEntity<Void> update(@RequestBody UserRequest request) {
        service.updateUser(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_SEARCH')")
    @GetMapping("/search")
    @ApiOperation(value = "Obtém usuários por numero da matricula ou busca por nome", authorizations =
            {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")
    })
    public ResponseEntity<List<UserResponse>> searchByNameOrRegistrationNumber(
            @ApiParam(value = "Texto contendo parametro de busca (numero da matricula ou nome do usuario)", required =
                    true)
            @RequestParam String nameOrRegNumber) {
        return ResponseEntity.ok(service.searchByNameOrRegistrationNumber(nameOrRegNumber));
    }

    @PreAuthorize("hasRole('ROLE_USERS_REGISTRATION_SEARCH')")
    @PostMapping("/ids")
    @ApiOperation(value = "Buscar todos os usuários com respectivos ids do KeyCloak", authorizations =
            {@Authorization(value =
                    "OAuth2")})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Erro interno")})
    public ResponseEntity<List<UserResponse>> findAllByIds(
            @ApiParam(value = "Lista de ids de usuários no KeyCloak") @RequestBody List<String> keyCloakIds) {
        return ResponseEntity.ok(service.findAllByKeyCloakId(keyCloakIds));
    }


}
