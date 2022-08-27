package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.bassani.examplemodellib.util.Mocks.jsonKeysFrom;

public class ExpirationParameterMocks {

    public static String[] jsonKeys() {
        return jsonKeysFrom(ExpirationParameterResponse.class);
    }

    public static List<ExpirationParameterResponse> mockedExpirationParameterList() {
        return List.of(
                ExpirationParameterResponse.builder().id(1L).description("test 1").build(),
                ExpirationParameterResponse.builder().id(2L).description("test 2").build());
    }

    public static ExpirationParameterResponse mockedExpirationParameterResponse() {
        return ExpirationParameterResponse.builder()
                .id(1L)
                .expirationFlow(new ExpirationDTO(1L, "TESTE 1"))
                .creationDateTime(LocalDateTime.now())
                .qtyExpirationDay(2)
                .description("Parametros de teste")
                .userId("ASDASDWA-JKOOGHFOFGH-GRQEWWRGEQW")
                .build();
    }
    public static String mockedExpirationParameterAsJson() {
        return asJson(mockedExpirationParameterList());
    }
}