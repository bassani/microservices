package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.RecipientMessageReadUnReadRequestMock;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.RecipientService;
import org.bassani.examplemodellib.domain.request.RecipientMessageReadUnReadRequest;
import org.bassani.examplemodellib.util.MessageBundle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(RecipientController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecipientControllerTest {

    private static final String ENDPOINT = "/purchases/recipient";

    @Autowired private MockMvc mockMvc;

    @MockBean private RecipientService service;

    @Autowired private ObjectMapper mapper;

    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles = "NOTIFICATION_UPDATE_FLAG_READ")
    @Test
    @DisplayName("PUT - /message-status - OK")
    void shouldUpdateMessageStatus() throws Exception {

        //scenario
        RecipientMessageReadUnReadRequest request =
                RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_complete();
        BDDMockito.doNothing()
                .when(service)
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));

        //action
        this.mockMvc.perform(put(ENDPOINT + "/message-status").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8.name())).andExpect(status().isNoContent()); //validation

        BDDMockito.verify(service).defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));
    }

    @WithMockUser(roles = "NOTIFICATION_UPDATE_FLAG_READ")
    @Test
    @DisplayName("PUT - /message-status - BAD_REQUEST")
    void shoudThrowException_whenIDsIsNull() throws Exception {

        //scenario
        RecipientMessageReadUnReadRequest request =
                RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_withoutIds();
        BDDMockito.doNothing()
                .when(service)
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));

        //action
        this.mockMvc.perform(put(ENDPOINT + "/message-status").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))//validation
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("ids")))//validation
        ;


        BDDMockito.verify(service, Mockito.never())
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));
        BDDMockito.verifyNoMoreInteractions(service);
    }

    @WithMockUser(roles = "NOTIFICATION_UPDATE_FLAG_READ")
    @Test
    @DisplayName("PUT - /message-status - BAD_REQUEST")
    void shoudThrowException_whenIDsIsEmpty() throws Exception {

        //scenario
        RecipientMessageReadUnReadRequest request =
                RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_idsEmpty();
        BDDMockito.doNothing()
                .when(service)
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));

        //action
        this.mockMvc.perform(put(ENDPOINT + "/message-status").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))//validation
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("ids")))//validation
        ;


        BDDMockito.verify(service, Mockito.never())
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));
        BDDMockito.verifyNoMoreInteractions(service);
    }

    @WithMockUser(roles = "NOTIFICATION_UPDATE_FLAG_READ")
    @Test
    @DisplayName("PUT - /message-status - BAD_REQUEST")
    void shoudThrowException_whenFlagReadIsNull() throws Exception {

        //scenario
        RecipientMessageReadUnReadRequest request =
                RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_withoutFlagRead();
        BDDMockito.doNothing()
                .when(service)
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));

        //action
        this.mockMvc.perform(put(ENDPOINT + "/message-status").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))//validation
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("read")))//validation
        ;


        BDDMockito.verify(service, Mockito.never())
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));
        BDDMockito.verifyNoMoreInteractions(service);
    }


    @WithMockUser(roles = "NOTIFICATION_UPDATE_FLAG_READ")
    @Test
    @DisplayName("PUT - /message-status - BAD_REQUEST")
    void shoudThrowException_whenKCUserIDIsNull() throws Exception {

        //scenario
        RecipientMessageReadUnReadRequest request =
                RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_withoutKCUserID();
        BDDMockito.doNothing()
                .when(service)
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));

        //action
        this.mockMvc.perform(put(ENDPOINT + "/message-status").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message").value(MessageBundle.getMessage("validation.failure.message")))//validation
                .andExpect(jsonPath("$.descriptions[0]").value(containsString("keycloakUserId")))//validation
        ;


        BDDMockito.verify(service, Mockito.never())
                .defineMessageReadUnread(BDDMockito.any(RecipientMessageReadUnReadRequest.class));
        BDDMockito.verifyNoMoreInteractions(service);
    }

}