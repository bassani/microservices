package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.UserResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserMock {

    public static List<UserResponse> getListResponse() {
        return List.of(
                mockedUserResponse("Usuário", "Um", "9669b7c7-4fea-480c-84db-11537e0110b2", "teste1@rd.com.br",
                        223344L),
                mockedUserResponse("Usuário", "Dois", "a669b7c7-4fea-480c-84db-11537e0110b2", "teste2@rd.com.br",
                        332455L),
                mockedUserResponse("Usuário", "Três", "b569b7c7-4fea-480c-84db-11537e0110b2", "teste3@rd.com.br",
                        1126677L)
        );
    }

    public static UserResponse mockedUserResponse(String firstName, String lastName, String keyCloakId, String email,
                                                  Long registrationNumber) {
        return UserResponse.builder()
                .firstName(firstName)
                .lastName(lastName)
                .keyCloakUserId(keyCloakId)
                .email(email)
                .registrationNumber(registrationNumber)
                .build();
    }

    public static UserRequest mockedUserRequest(String firstName, String lastName, String keyCloakId, String email) {
        return UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .keycloakUserId(keyCloakId)
                .email(email)
                .build();
    }
}
