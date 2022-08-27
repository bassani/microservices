package org.bassani.examplebff.mock;


import org.bassani.examplemodellib.domain.response.KeyCloakGroupDetailResponse;
import org.bassani.examplemodellib.domain.response.KeyCloakUserAttributeDetailResponse;
import org.bassani.examplemodellib.domain.response.KeyCloakUserDetailResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.bassani.examplemodellib.util.Mocks.asJson;

@Slf4j
public class KeyCloakMock {

    public static KeyCloakUserDetailResponse mockedKeyCloakUserDetailResponse(String id, String firstName,
                                                                              String email, KeyCloakUserAttributeDetailResponse attributes) {
        return KeyCloakUserDetailResponse.builder().id(id)
                .firstName(firstName)
                .email(email)
                .attributes(attributes)
                .build();
    }

    public static KeyCloakUserAttributeDetailResponse mockedKeyCloakUserAttributeResponse(Long registrationNumber,
                                                                                          Long areaCode,
                                                                                          Long profileCode) {
        return KeyCloakUserAttributeDetailResponse.builder()
                .registrationNumber(Arrays.asList(registrationNumber))
                .cdArea(Arrays.asList(areaCode))
                .cdPerfil(Arrays.asList(profileCode))
                .build();
    }

    public static String mockedKeyCloakUserAsJson() {
        return asJson(List.of(mockedKeyCloakUserDetailResponse(UUID.randomUUID().toString(), "User", "email@rd.com.br",
                mockedKeyCloakUserAttributeResponse(123L, 0L, 1L))));
    }

    public static List<KeyCloakUserDetailResponse> mockedListKeyCloakUser() {
        return List.of(
                mockedKeyCloakUserDetailResponse(UUID.randomUUID().toString(), "Usuário Um", "teste1@rd.com.br",
                        mockedKeyCloakUserAttributeResponse(234120L, 1L, 0L)),
                mockedKeyCloakUserDetailResponse(UUID.randomUUID().toString(), "Usuário Dois", "teste2@rd.com.br",
                        mockedKeyCloakUserAttributeResponse(634120L, 1L, 1L)),
                mockedKeyCloakUserDetailResponse(UUID.randomUUID().toString(), "Usuário Tres", "teste3@rd.com.br",
                        mockedKeyCloakUserAttributeResponse(934120L, 1L, 0L))
        );
    }

    public static List<KeyCloakGroupDetailResponse> mockedListKeyCloakGroup() {
        return List.of(KeyCloakGroupDetailResponse.builder().id(UUID.randomUUID().toString()).name("Administrador").build(),
                KeyCloakGroupDetailResponse.builder().id(UUID.randomUUID().toString()).name("Analista").build(),
                KeyCloakGroupDetailResponse.builder().id(UUID.randomUUID().toString()).name("Coodenador").build(),
                KeyCloakGroupDetailResponse.builder().id(UUID.randomUUID().toString()).name("Gerente").build()
        );
    }
}
