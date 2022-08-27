package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.RecipientService;
import org.bassani.examplemodellib.domain.request.RecipientMessageReadUnReadRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases/recipient")
@Slf4j
@Api(tags = {"Recipient"})
public class RecipientController {

    private final RecipientService service;

    @PreAuthorize("hasRole('ROLE_NOTIFICATION_UPDATE_FLAG_READ')")
    @PutMapping("/message-status")
    @ApiOperation(value = "Atualiza o status da mensagem para Lida/Não Lida",
            authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Mensagem atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Erro interno")
    })
    public ResponseEntity<Void> defineMessageReadUnread(@RequestBody @Valid RecipientMessageReadUnReadRequest request) {
        service.defineMessageReadUnread(request);
        return ResponseEntity.noContent().build();
    }


}