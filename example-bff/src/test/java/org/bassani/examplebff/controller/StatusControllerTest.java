package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationStatusService;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(StatusController.class)
@AutoConfigureMockMvc(addFilters = false)
class StatusControllerTest {

    private static final String ENDPOINT = "/purchases/status";

    @Autowired private MockMvc mvc;
    @MockBean private SimulationStatusService service;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="STATUS_GETALL")
    @Test
    @DisplayName("GET /status -> OK")
    void shouldGetAllStatus() throws Exception {
        List<SimulationStatusResponse> simulationStatusResponses =
                SimulationMocks.mockedSimulationStatusResponses();

        SimulationStatusResponse simulationStatusResponse = simulationStatusResponses.get(0);

        when(service.getAllStatus()).thenReturn(simulationStatusResponses);

        mvc.perform(get(ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(simulationStatusResponses.size()))
                .andExpect(jsonPath("$[0].id").value(simulationStatusResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(simulationStatusResponse.getName()))
                .andExpect(jsonPath("$[0].active").value(simulationStatusResponse.getActive()))
                .andExpect(jsonPath("$[0].registerOperator").value(simulationStatusResponse.getRegisterOperator()))
                .andExpect(jsonPath("$[0].active").value(simulationStatusResponse.getActive()));
    }

}