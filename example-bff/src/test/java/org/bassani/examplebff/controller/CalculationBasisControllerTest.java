package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.CalculationBasisMocks;
import org.bassani.examplebff.service.CalculationBasisService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.response.CalculationBasisOptionGroupResponse;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CalculationBasisController.class)
class CalculationBasisControllerTest {

    private static final String URL = "/purchases/calculationBasis";

    @Autowired private MockMvc mvc;
    @MockBean private CalculationBasisService service;
    @MockBean private KeyCloakService keyCloakService;

    @Test
    @DisplayName("GET /calculationBasis -> OK, 1+ elements")
    @WithMockUser(roles="CALCULATIONBASIS_GETALL")
    public void whenGetIsPerformed_thenOk() throws Exception {
        List<CalculationBasisOptionGroupResponse> responses = CalculationBasisMocks.mockedCalculationBasis();
        CalculationBasisOptionGroupResponse firstResponse = responses.get(0);

        when(service.findAll()).thenReturn(responses);

        mvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(responses.size()))
                .andExpect(jsonPath("$[0].name").value(firstResponse.getName()))
                .andExpect(jsonPath("$[0].id").value(firstResponse.getId()));
    }

    @Test
    @WithMockUser(roles="CALCULATIONBASIS_GETALL")
    @DisplayName("GET /calculationBasis -> OK, 0 elements")
    void givenEmptyDBTable_whenGelAllMenusIsPerformed_thenEmptyJSONIsReturned() throws Exception {
        int actualMenuNumber = 0;

        when(service.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(actualMenuNumber));
    }
}