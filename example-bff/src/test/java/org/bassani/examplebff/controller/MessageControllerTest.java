package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.MessageMock;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.MessageService;
import org.bassani.examplemodellib.domain.response.MessageUserNotificationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    private static final String ENDPOINT = "/purchases/messages";

    @Autowired private MockMvc mvc;
    @MockBean private MessageService service;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="NOTIFICATION_GET_MESSAGES")
    @Test
    @DisplayName("GET / -> OK")
    void shouldGetAllStatus() throws Exception {
        Page<MessageUserNotificationResponse> messageResponses = MessageMock.mockedResponsePage();

        MessageUserNotificationResponse response = messageResponses.getContent().get(0);

        when(service.findAllByRecipient(any(Pageable.class))).thenReturn(messageResponses);

        mvc.perform(get(ENDPOINT.concat("/?orderByExpirationDate=0")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(messageResponses.getTotalElements()))
                .andExpect(jsonPath("$.content.[0].id").value(response.getId()))
                .andExpect(jsonPath("$.content.[0].keycloakUserId").value(response.getKeycloakUserId()))
                .andExpect(jsonPath("$.content.[0].read").value(response.getRead()));
    }

    @WithMockUser(roles="NOTIFICATION_GET_MESSAGES")
    @Test
    @DisplayName("GET /?orderByExpirationDate=1 -> OK ")
    void shouldGetAllStatusOrderedByExpiration() throws Exception {
        Page<MessageUserNotificationResponse> messageResponses = MessageMock.mockedResponsePage();

        MessageUserNotificationResponse response = messageResponses.getContent().get(0);

        when(service.findAllByRecipient(any(Pageable.class))).thenReturn(messageResponses);

        mvc.perform(get(ENDPOINT.concat("/?orderByExpirationDate=1")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(messageResponses.getTotalElements()))
                .andExpect(jsonPath("$.content.[0].id").value(response.getId()))
                .andExpect(jsonPath("$.content.[0].keycloakUserId").value(response.getKeycloakUserId()))
                .andExpect(jsonPath("$.content.[0].read").value(response.getRead()));
    }
}