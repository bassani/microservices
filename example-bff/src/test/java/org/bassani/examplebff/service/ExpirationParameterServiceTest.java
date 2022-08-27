package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.ExpirationParameterMocks;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterRequest;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpirationParameterServiceTest {

    @Mock private CoreClient coreClient;

    @Mock private KeyCloakUtils keyCloakUtils;

    @InjectMocks private ExpirationParameterService service;

    @Test
    void returnAllPaginated() {
        ExpirationParameterResponse expResponse = ExpirationParameterResponse.builder()
                .id(1L)
                .expirationFlow(new ExpirationDTO(1L, "TESTE 1"))
                .creationDateTime(LocalDateTime.now())
                .qtyExpirationDay(2)
                .description("Parametros de teste")
                .userId("ASDASDWA-JKOOGHFOFGH-GRQEWWRGEQW")
                .build();

        Page<ExpirationParameterResponse> response = new PageImpl<>(Collections.singletonList(expResponse));

        when(coreClient.getExpirationParameters(any())).thenReturn(response);

        assertEquals(response, service.getAllPaginated(any()));
    }

    @Test
    void findById() {
        var response = ExpirationParameterResponse.builder()
                .id(1L)
                .expirationFlow(new ExpirationDTO(1L, "TESTE 1"))
                .creationDateTime(LocalDateTime.now())
                .qtyExpirationDay(2)
                .description("Parametros de teste")
                .userId("ASDASDWA-JKOOGHFOFGH-GRQEWWRGEQW")
                .build();

        when(coreClient.getExpirationParameterById(any())).thenReturn(response);

        assertEquals(response, service.findById(any()));
    }

    @Test
    void save() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            LocalDateTime now = LocalDateTime.now();

            ExpirationParameterRequest request = ExpirationParameterRequest.builder()
                    .flowId(1)
                    .creationDateTime(now)
                    .qtyExpirationDay(2)
                    .description("Parametros de teste")
                    .build();

            ExpirationParameterResponse expected = ExpirationParameterResponse.builder()
                    .id(1L)
                    .expirationFlow(new ExpirationDTO(1L, "TESTE 1"))
                    .creationDateTime(now)
                    .qtyExpirationDay(2)
                    .description("Parametros de teste")
                    .userId("ASDASDWA-JKOOGHFOFGH-GRQEWWRGEQW")
                    .build();

            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getKeycloakUserId)
                    .thenReturn("ASDASDWA-JKOOGHFOFGH-GRQEWWRGEQW");

            when(coreClient.saveExpirationParameter(any(ExpirationParameterRequest.class))).thenReturn(expected);

            ExpirationParameterResponse save = service.save(request);

            assertEquals(expected, save);
        }
    }

    @Test
    void returnResume() {
        ExpirationParameterResponse expResponse = ExpirationParameterMocks.mockedExpirationParameterResponse();

        List<ExpirationParameterResponse> response = List.of(expResponse);

        when(coreClient.findLatestExpirationParameters()).thenReturn(response);

        assertEquals(response, service.findLatest());
    }
}