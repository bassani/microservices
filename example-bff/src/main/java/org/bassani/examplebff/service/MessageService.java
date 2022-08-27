package org.bassani.examplebff.service;

import org.bassani.examplebff.client.NotifyClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.response.MessageUserNotificationResponse;
import org.bassani.examplemodellib.exception.NotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final NotifyClient notifyClient;

    public Page<MessageUserNotificationResponse> findAllByRecipient(Pageable page) {
        String keycloakUserId = KeyCloakUtils.getKeycloakUserId();
        try {
            Page<MessageUserNotificationResponse> notifications = notifyClient.findAllByRecipient(keycloakUserId, page);
            return new PageImpl<>(notifications.getContent(), page, notifications.getTotalElements());
        }catch (FeignException.NotFound e) {
            throw new NotFoundException("Nenhuma notificação", "Não existem notificações.");
        }
    }
}
