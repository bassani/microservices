package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ExpirationService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ExpirationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpirationControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private ExpirationService service;

    @MockBean private KeyCloakService keyCloakService;

    private static final String ENDPOINT = "/purchases/expirations";

    @Test
    @WithMockUser(roles = "")
    @DisplayName("GET findAllPaginated -> OK ")
    void startApprovalProcessInstance() throws Exception {
        List<ExpirationDTO> response = Arrays.asList(new ExpirationDTO(1L, "TESTE 1"),
                new ExpirationDTO(2L, "TESTE 2"));

        when(service.getAllPaginated(any())).thenReturn(new PageImpl<>(response));

        mvc.perform(get(ENDPOINT).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}