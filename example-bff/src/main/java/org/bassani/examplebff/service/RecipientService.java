package org.bassani.examplebff.service;

import org.bassani.examplebff.client.NotifyClient;
import org.bassani.examplemodellib.domain.request.RecipientMessageReadUnReadRequest;
import org.bassani.examplemodellib.exception.NullParameterException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class RecipientService {

    private final NotifyClient notifyClient;

    @Transactional
    public void defineMessageReadUnread(RecipientMessageReadUnReadRequest request) {
        inputValidation(request);
        notifyClient.defineMessageReadUnread(request);
    }

    private void inputValidation(RecipientMessageReadUnReadRequest request) {
        RecipientMessageReadUnReadRequest requestParam = ofNullable(request).orElseThrow(NullParameterException::new);
        List<Long> idsParam = ofNullable(request.getIds()).orElseThrow(NullParameterException::new);
        List<Long> idsParamIsEmpty = ofNullable(request.getIds()).filter(ObjectUtils::isNotEmpty)
                .orElseThrow(NullParameterException::new);
        String kcUserIdParam = ofNullable(request.getKeycloakUserId()).orElseThrow(NullParameterException::new);
        Boolean readParam = ofNullable(request.getRead()).orElseThrow(NullParameterException::new);
    }
}