package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.MessageService;
import org.bassani.examplemodellib.domain.response.MessageUserNotificationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases/messages")
@RequiredArgsConstructor
@Validated
@Api(tags = { "Notify messages" })
public class MessageController {
    private final MessageService notificationService;

    @PreAuthorize("hasRole('ROLE_NOTIFICATION_GET_MESSAGES')")
    @GetMapping(value = "/", params = {"orderByExpirationDate"})
    @ApiOperation(value = "Obtém as notificações do usuário", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 400, message = "ID inválido"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<MessageUserNotificationResponse>> getNotifications(
            @RequestParam(defaultValue = "false") Boolean orderByExpirationDate,
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({@SortDefault(sort = "read", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "notificationDate", direction = Sort.Direction.DESC)
            }) Pageable pageable) {
        if (orderByExpirationDate) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by( Sort.Direction.ASC, "read")
                            .and(Sort.by(Sort.Direction.ASC, "expirationDate"))
                    );
        }
        return ResponseEntity.ok(notificationService.findAllByRecipient(pageable));
    }
}
