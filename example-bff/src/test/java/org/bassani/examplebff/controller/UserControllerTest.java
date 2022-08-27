package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.UserMock;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.UserService;
import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.PositionResponse;
import org.bassani.examplemodellib.domain.response.UserResponse;
import org.bassani.examplemodellib.util.Mocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    private final Integer PAGE = 0;
    private final Integer SIZE = 25;
    private final Sort SORT = Sort.by("fisrtName").ascending();

    @Autowired
    private MockMvc mvc;

    @MockBean private UserService service;

    @MockBean private KeyCloakService keyCloakService;

    private String ENDPOINT = "/purchases/users";

    @WithMockUser(roles="USERS_REGISTRATION_GET")
    @Test
    @DisplayName("GET - /users - OK")
    void whenGetReturnWithRequestOk() throws Exception {

        List<UserResponse> response = UserMock.getListResponse();

        doReturn(new PageImpl<>(response))
                .when(service)
                .getAllUsers(PageRequest.of(PAGE, SIZE, SORT), null);

        mvc.perform(get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="USERS_REGISTRATION_GET_POSITIONS")
    @Test
    @DisplayName("GET - /users/positions - OK")
    void whenGetPositionsReturnWithRequestOk() throws Exception {

        List<PositionResponse> response = List.of(
                PositionResponse.builder().id("ac2c906e-7dfc-4b29-9471-36abc91f2992").name("Administrador").build(),
                PositionResponse.builder().id("dc3c906e-7dfc-4b29-9471-36abc91f2992").name("Analista").build());

        doReturn(response)
                .when(service)
                .getAllPositions();

        mvc.perform(get(ENDPOINT.concat("/positions"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="USERS_REGISTRATION_GET_USERPOSITION")
    @Test
    @DisplayName("GET - /users/{id}/position -> OK")
    void whenGetUserPositionReturnWithRequestOk() throws Exception {

        PositionResponse position = PositionResponse.builder()
                .id("ac2c906e-7dfc-4b29-9471-36abc91f2992")
                .name("Administrador")
                .build();

        doReturn(position)
                .when(service)
                .getUserPosition(Mockito.anyString());

        mvc.perform(get(ENDPOINT.concat("/{id}/position"), "9669b7c7-4fea-480c-84db-11537e0110b2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="USERS_REGISTRATION_SAVE")
    @Test
    @DisplayName("POST - /users -> OK")
    void whenSaveReturnWithStatusCreated() throws Exception {
        UserRequest request = UserMock.mockedUserRequest("Usuário", "Teste",
                null, "teste@rd.com.br");
        mvc.perform(post(ENDPOINT)
                        .content(Mocks.asJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @WithMockUser(roles="USERS_REGISTRATION_UPDATE")
    @Test
    @DisplayName("PUT - /users -> OK")
    void whenUpdateReturnWithRequestOk() throws Exception {
        UserRequest request = UserMock.mockedUserRequest("Usuário", "Teste",
                "9669b7c7-4fea-480c-84db-11537e0110b2", "teste@rd.com.br");
        mvc.perform(put(ENDPOINT)
                        .content(Mocks.asJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="USERS_REGISTRATION_SEARCH")
    @Test
    @DisplayName("GET - /users/search?nameOrRegistrationNumber=Usuário -> OK")
        void whenSearchByNameOrRegistrationNumberReturnWithRequestOk() throws Exception {
        List<UserResponse> response = Arrays.asList(UserMock.mockedUserResponse("Usuário", "Teste",
                "9669b7c7-4fea-480c-84db-11537e0110b2", "teste@rd.com.br", null));

        doReturn(response)
                .when(service)
                .searchByNameOrRegistrationNumber(Mockito.anyString());

        mvc.perform(get(ENDPOINT.concat("/search")).param("nameOrRegNumber","Usuário")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="USERS_REGISTRATION_SEARCH")
    @Test
    @DisplayName("POST - /users/ids -> OK")
    void whenSearchByKeyCloakIdsReturnWithRequestOk() throws Exception {

        List<String> keyCloaksIds = Arrays.asList("9669b7c7-4fea-480c-84db-11537e0110b2");

        List<UserResponse> response = Arrays.asList(UserMock.mockedUserResponse("Usuário", "Teste",
                "9669b7c7-4fea-480c-84db-11537e0110b2", "teste@rd.com.br", null));

        doReturn(response)
                .when(service)
                .findAllByKeyCloakId(Mockito.anyList());

        mvc.perform(post(ENDPOINT.concat("/ids"))
                        .content(Mocks.asJson(keyCloaksIds))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
