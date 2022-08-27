package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.KeyCloakMock;
import org.bassani.examplebff.mock.UserMock;
import org.bassani.examplemodellib.domain.request.UserRequest;
import org.bassani.examplemodellib.domain.response.KeyCloakGroupDetailResponse;
import org.bassani.examplemodellib.domain.response.KeyCloakUserDetailResponse;
import org.bassani.examplemodellib.domain.response.PositionResponse;
import org.bassani.examplemodellib.domain.response.UserResponse;
import org.bassani.examplemodellib.mapper.PositionMapper;
import org.bassani.examplemodellib.mapper.UserMapper;
import org.bassani.examplemodellib.util.Mocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.created;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = {"endpoint=/"})
public class UserIT extends IntegrationTestBase {

    @Test
    @DisplayName("Get All Users - GET /users?page=2&size=3 -> OK")
    void whenGetAllUsers_thenReturnOk() {
        //Assumptions
        int pageSize = 3;
        int pageNumber = 2;
        int numberOfElements = 3;

        List<UserResponse> mockBffResponse =
                KeyCloakMock.mockedListKeyCloakUser().stream().map(UserMapper.userMapper()::keyCloakUserToResponse)
                        .collect(Collectors.toList());
        Page<UserResponse> page = new PageImpl<>(mockBffResponse, PageRequest.of(pageNumber, pageSize), numberOfElements);

        var expectedEmails = mockBffResponse.stream()
                .map(UserResponse::getEmail)
                .collect(Collectors.toList()).toArray();

        stubFor(get(msOperatorPath.concat(String.format( "users?page=%d&size=%d", pageNumber, pageSize)))
                .willReturn(okJson(Mocks.asJson(page))));

        //Actions
        var response =
                rest.getForEntity(String.format("%s?page=%d&size=%d", thisMsUri.concat("users"),
                                pageNumber, pageSize),
                        String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        var msActualResponseJson = response.getBody();
        assertThat(msActualResponseJson, hasJsonPath("$.numberOfElements", is(numberOfElements)));
        assertThat(msActualResponseJson, hasJsonPath("$.content[0:3].email", containsInAnyOrder(expectedEmails)));

    }

    @Test
    @DisplayName("Save User - POST  /users -> Created")
    void whenSaveUser_thenReturnCreated() {

        UserRequest request = UserMock.mockedUserRequest("Usuario",
                "Teste", null, "teste@rd.com.br");

        stubFor(post(msOperatorPath.concat("users")).willReturn(created()));

        //Actions
        var response =
                rest.postForEntity(thisMsUri.concat("users"), request, Void.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(201));
    }

    @Test
    @DisplayName("Update User - PUT /users -> Ok")
    void whenUpdateUser_thenReturnOk() {

        UserRequest request = UserMock.mockedUserRequest("Usuario",
                "Teste", "9669b7c7-4fea-480c-84db-11537e0110b2", "teste@rd.com.br");

        stubFor(put(msOperatorPath.concat("users"))
                .willReturn(ok()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON_UTF8));

        //Actions
        ResponseEntity<String> response = rest.exchange(thisMsUri.concat("users"), HttpMethod.PUT,
                new HttpEntity<>(request, headers), String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
    }

    @Test
    @DisplayName("Get All Positions - GET /users/positions -> Ok")
    void whenGetAllPositions_thenReturnOk() {
        List<KeyCloakGroupDetailResponse> keyCloakGroupMock = KeyCloakMock.mockedListKeyCloakGroup();
        List<PositionResponse> groupsResponse = keyCloakGroupMock.stream()
                .map(PositionMapper.positionMapper()::keyCloakGroupToResponse)
                .collect(Collectors.toList());
        var expectedResponse = groupsResponse.toArray();

        stubFor(get(msOperatorPath.concat(String.format( "users/positions")))
                .willReturn(okJson(Mocks.asJson(groupsResponse))));

        //Actions
        var response =
                rest.getForEntity(thisMsUri.concat("users/positions"), String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        assertThat(response.getBody(), hasJsonPath("$.length()", is(expectedResponse.length)));

        verify(getRequestedFor(urlEqualTo(msOperatorPath.concat(String.format( "users/positions")))));
    }

    @Test
    @DisplayName("Get User Position - GET /users/{id}/position -> Ok")
    void whenGetUserPosition_thenReturnOk() {
        String userId = UUID.randomUUID().toString();

        KeyCloakGroupDetailResponse keyCloakGroupMock = KeyCloakGroupDetailResponse.builder()
                .id(UUID.randomUUID().toString())
                .name("Coordenador")
                .build();

        PositionResponse positionResponse = PositionMapper.positionMapper().keyCloakGroupToResponse(keyCloakGroupMock);

        var msOperatorResponse = Mocks.asJson(positionResponse);

        stubFor(get(String.format("%susers/%s/position", msOperatorPath, userId))
                .willReturn(okJson(msOperatorResponse)));

        //Actions
        var response =
                rest.getForEntity(String.format("%susers/%s/position", thisMsUri, userId), String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        assertThat(response.getBody(), hasJsonPath("$.name", is(keyCloakGroupMock.getName())));

        verify(getRequestedFor(urlEqualTo(String.format("%susers/%s/position", msOperatorPath, userId))));
    }

    @Test
    @DisplayName("Search User By Registration Number - GET /users/search?nameOrRegNumber=213456 -> Ok")
    void whenSearchUserByRegistrationNumber_thenReturnOk() {
        String paramName = "Alex";

        KeyCloakUserDetailResponse keyCloakUserDetailMock =
                KeyCloakMock.mockedKeyCloakUserDetailResponse(UUID.randomUUID().toString(), paramName,
                        "alex@rd.com.br", null );
        String msKeyCloakResponse = Mocks.asJson(Arrays.asList(keyCloakUserDetailMock));

        stubFor(get(String.format("%susers/search?nameOrRegNumber=%s", msOperatorPath, paramName))
                .willReturn(okJson(msKeyCloakResponse)));

        //Actions
        var response =
                rest.getForEntity(
                        thisMsUri.concat(String.format("users/search?nameOrRegNumber=%s", paramName)),
                        String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        assertThat(response.getBody(), hasJsonPath("$[0].firstName", is(keyCloakUserDetailMock.getFirstName())));

        verify(getRequestedFor(urlEqualTo(String.format("%susers/search?nameOrRegNumber=%s", msOperatorPath,
                paramName))));
    }

}
