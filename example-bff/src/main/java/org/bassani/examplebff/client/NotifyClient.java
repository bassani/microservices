package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorNotifyOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.request.RecipientMessageReadUnReadRequest;
import org.bassani.examplemodellib.domain.response.MessageUserNotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@FeignClient(name = "ms-notify", url = "${ms-example-notify.url}",
        configuration = PurchaseSimulatorNotifyOAuth2FeignConfig.class)
public interface NotifyClient {

    @PutMapping("/recipient/message-status")
    void defineMessageReadUnread(@RequestBody RecipientMessageReadUnReadRequest request);

    @GetMapping("/messages/{keycloakUserId}")
    Page<MessageUserNotificationResponse> findAllByRecipient(@Valid @NotNull @PathVariable String keycloakUserId,
                                                             @PageableDefault(page = 0, size = 10) Pageable page);

}