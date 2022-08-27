package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.client.KeyCloakClient;
import org.bassani.examplebff.client.SourceOperatorClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.request.KeyCloakUserDetailAttributeRequest;
import org.bassani.examplemodellib.domain.request.KeyCloakUserDetailRequest;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import org.bassani.examplemodellib.domain.response.ProfileResponse;
import org.bassani.examplemodellib.exception.GroupNotFoundException;
import org.bassani.examplemodellib.exception.ProfileNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.bassani.examplemodellib.mapper.UserMapper.userMapper;

@Service
@AllArgsConstructor
@Slf4j
public class KeyCloakService {

    private final KeyCloakClient keyCloakClient;
    private final SourceOperatorClient sourceOperatorClient;
    private final CoreClient coreClient;

    public void updateTokenAttribute() {

        KeyCloakUserDetailAttributeRequest.KeyCloakUserDetailAttributeRequestBuilder attributesBuilder =
                KeyCloakUserDetailAttributeRequest
                .builder();

        Optional<OperatorResponse> optionalOperatorResponse = getOperator();
        if (optionalOperatorResponse.isPresent()) {
            Long cdOperador = optionalOperatorResponse.get().getCode();
            attributesBuilder.cdOperador(cdOperador);
        }

        ProfileResponse profile = getProfile();
        Long cdPerfil = profile.getId();
        Long cdArea = 1L;

        attributesBuilder.cdPerfil(cdPerfil);
        attributesBuilder.cdArea(cdArea);

        KeyCloakUserDetailRequest keyCloakUserDetailRequest = KeyCloakUserDetailRequest.builder()
                .attributes(attributesBuilder.build())
                .build();

        String keycloakUserId = KeyCloakUtils.getKeycloakUserId();

        keyCloakClient.updateUserAttribute(keycloakUserId, keyCloakUserDetailRequest);

        if(optionalOperatorResponse.isPresent()){
            KeyCloakUtils.addCdOperatorCurrentToken(optionalOperatorResponse.get().getCode());
        }
        if (cdPerfil != null) {
            KeyCloakUtils.addCdPerfilCurrentToken(cdPerfil);
        }
        if (cdArea != null) {
            KeyCloakUtils.addCdAreaCurrentToken(cdArea);
        }

    }

    private Optional<OperatorResponse> getOperator() {
        return keyCloakClient.getAllUsersWithoutPagination(false, KeyCloakUtils.getEmail()).stream()
                .map(userMapper()::keyCloakUserToOperatorResponse).findFirst();
    }

    private ProfileResponse getProfile() {
        String group = KeyCloakUtils.getGroup().orElseThrow(GroupNotFoundException::new);
        return coreClient.getProfile(group).orElseThrow(ProfileNotFoundException::new);
    }
}
