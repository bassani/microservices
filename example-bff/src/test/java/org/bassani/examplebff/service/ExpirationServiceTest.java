package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpirationServiceTest {

    @Mock private CoreClient coreClient;

    @InjectMocks private ExpirationService service;

    @Test
    void returnAllPaginated() {
        Page<ExpirationDTO> response = new PageImpl<>(
                Arrays.asList(new ExpirationDTO(1L, "TESTE 1"), new ExpirationDTO(2L, "TESTE 2")));

        when(coreClient.getExpirations(any())).thenReturn(response);

        assertEquals(response, service.getAllPaginated(any()));
    }

}