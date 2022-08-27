package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpirationService {

    private final CoreClient coreClient;

    public Page<ExpirationDTO> getAllPaginated(Pageable pageable) {
        return coreClient.getExpirations(pageable);
    }

}
