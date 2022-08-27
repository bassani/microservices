package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterRequest;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpirationParameterService {

    private final CoreClient coreClient;

    public Page<ExpirationParameterResponse> getAllPaginated(Pageable pageable) {
        return coreClient.getExpirationParameters(pageable);
    }

    public ExpirationParameterResponse findById(Long id) {
        return coreClient.getExpirationParameterById(id);
    }

    public ExpirationParameterResponse save(ExpirationParameterRequest request) {
        request.setUserId(KeyCloakUtils.getKeycloakUserId());
        return coreClient.saveExpirationParameter(request);
    }

    public List<ExpirationParameterResponse> findLatest() {
        return coreClient.findLatestExpirationParameters();
    }
}
