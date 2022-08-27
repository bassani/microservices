package org.bassani.examplebff.service;

import org.bassani.examplebff.client.SourceOperatorClient;
import org.bassani.examplebff.mock.UserMock;
import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.KeyCloakUserAttributeDetailResponse;
import org.bassani.examplemodellib.domain.response.KeyCloakUserDetailResponse;
import org.bassani.examplemodellib.domain.response.PositionResponse;
import org.bassani.examplemodellib.domain.response.UserResponse;
import org.bassani.examplemodellib.exception.UserNotFoundException;
import org.bassani.examplemodellib.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final Integer PAGE = 0;
    private final Integer SIZE = 25;
    private final Sort SORT = Sort.by("name").ascending();

    @InjectMocks UserService service;

    @Mock private SourceOperatorClient sourceOperatorClient;

    @Test
    void testGetAllUsers() {
        KeyCloakUserAttributeDetailResponse attributesResponse = KeyCloakUserAttributeDetailResponse.builder()
                .cdArea(Arrays.asList(1L))
                .cdOperador(Arrays.asList(245678L))
                .cdPerfil(Arrays.asList(0L))
                .phoneNumer(Arrays.asList("11994792736"))
                .vacationReturnDate(Arrays.asList(LocalDate.of(2020, 1, 1)))
                .build();
        KeyCloakUserDetailResponse response = KeyCloakUserDetailResponse.builder()
                .id("553046d0-6365-46ca-999a-8c6a27c87cb8")
                .firstName("Maria")
                .lastName("da Silva")
                .email("msilva@rd.com.br")
                .enabled(true)
                .attributes(attributesResponse)
                .build();

        Page<UserResponse> userResponses =
                new PageImpl<>(Stream.of(response).map(UserMapper.userMapper()::keyCloakUserToResponse).collect(
                Collectors.toList()));

        Mockito.when(sourceOperatorClient.getAll(null, PAGE, SIZE))
                .thenReturn(userResponses);

        service.getAllUsers(PageRequest.of(PAGE, SIZE, SORT), null);

        verify(sourceOperatorClient, times(1)).getAll(null, PAGE, SIZE);
    }

    @Test
    void testGetAllPositions() {
        List<PositionResponse> response = List.of(
                PositionResponse.builder().id("fc2c907f-7dfc-4b29-9471-36abc91f2908").build());

        Mockito.when(sourceOperatorClient.getAllPositions()).thenReturn(response);

        service.getAllPositions();
        verify(sourceOperatorClient, times(1)).getAllPositions();
    }

    @Test
    void testGetUserPosition() {
        PositionResponse response =
                PositionResponse.builder().id("fc2c907f-7dfc-4b29-9471-36abc91f2908").build();

        Mockito.when(sourceOperatorClient.getUserPosition(Mockito.anyString())).thenReturn(response);
        service.getUserPosition("553046d0-6365-46ca-999a-8c6a27c87cb8");
        verify(sourceOperatorClient, times(1)).getUserPosition(Mockito.anyString());
    }

    @Test
    void testGetUserPosition_whenUserHasNoGroup() {
        Mockito.when(sourceOperatorClient.getUserPosition(Mockito.anyString())).thenReturn(new PositionResponse());
        service.getUserPosition("553046d0-6365-46ca-999a-8c6a27c87cb8");

        verify(sourceOperatorClient, times(1)).getUserPosition(Mockito.anyString());
    }

    @Test
    void testGetUserPosition_whenUserNotFound() {
        doThrow(UserNotFoundException.class)
                .when(sourceOperatorClient)
                .getUserPosition(Mockito.anyString());

        assertThrows(UserNotFoundException.class, () -> service.getUserPosition(Mockito.anyString()));

        verify(sourceOperatorClient, times(1)).getUserPosition(Mockito.anyString());
    }

    @Test
    void testSaveUser() {
        UserRequest userRequest = UserMock.mockedUserRequest("Usuário", "Teste", null,
                "teste@rd.com.br");

        service.saveUser(userRequest);

        verify(sourceOperatorClient, times(1)).saveUser(Mockito.any());
    }

    @Test
    void testSaveUpdate() {
        UserRequest userRequest = UserMock.mockedUserRequest("Usuário", "Teste",
                "9669b7c7-4fea-480c-84db-11537e0110b2", "teste@rd.com.br");

        service.updateUser(userRequest);

        verify(sourceOperatorClient, times(1)).editUser(Mockito.any());
    }


    @Test
    void testSearchByNameOrRegistrationNumber_whenSearchByRegNum() {
        String param = "2516789";
        List<UserResponse> response = Arrays.asList(UserMock.mockedUserResponse("Usuário", "Teste",
                "9669b7c7-4fea-480c-84db-11537e0110b2", "teste@rd.com.br", 2516789L));

        Mockito.when(sourceOperatorClient.searchByNameOrRegistrationNumber(Mockito.any())).thenReturn(response);

        service.searchByNameOrRegistrationNumber(param);

        verify(sourceOperatorClient, times(1)).searchByNameOrRegistrationNumber(Mockito.anyString());

    }


    @Test
    void testGetUsersByKeyCloakId() {
        List<String> keyCloakIds = Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        service.findAllByKeyCloakId(keyCloakIds);

        verify(sourceOperatorClient, times(1)).findAllByIds(Mockito.anyList());
    }
}
