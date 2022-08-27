package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ExpirationParameterService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ExpirationParameterController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpirationParameterControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private ExpirationParameterService service;

    @MockBean private KeyCloakService keyCloakService;

    private static final String ENDPOINT = "/purchases/expiration-parameters";

    @Test
    @WithMockUser(roles = "")
    @DisplayName("GET findLatest -> OK ")
    void getLatestExpirationParameter() throws Exception {
        List<ExpirationParameterResponse> response = List.of(ExpirationParameterResponse.builder()
                .creationDateTime(LocalDateTime.now())
                .id(1L)
                .description("test")
                .build());

        when(service.findLatest()).thenReturn(response);

        mvc.perform(get(ENDPOINT.concat("?latest=1")).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}