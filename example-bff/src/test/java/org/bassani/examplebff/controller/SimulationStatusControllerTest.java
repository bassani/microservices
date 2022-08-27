package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationStatusService;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SimulationStatusController.class)
@AutoConfigureMockMvc(addFilters = false)
class SimulationStatusControllerTest {

    private static final String ENDPOINT = "/purchases/simulations";

    @Autowired private MockMvc mvc;
    @MockBean private SimulationStatusService service;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="SIMULATIONSTATUS_GETSTATUSSIMULATION")
    @Test
    @DisplayName("GET /simulations/status -> OK")
    void shouldGetSimulationsCreatedInTheLastDefaultMinutesAndWithDefaultStatusByDefault() throws Exception {
        List<Long> expectedResponse = List.of(1L, 2L, 3L, 4L);
        var ids = expectedResponse.stream().map(Long::intValue).toArray();

        when(service.getSimulationIdsByStatus(null, null)).thenReturn(expectedResponse);

        mvc.perform(get(ENDPOINT.concat("/status")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedResponse.size()))
                .andExpect(jsonPath("$").value(contains(ids)));
    }

    @WithMockUser(roles="SIMULATIONSTATUS_GETSTATUSSIMULATION")
    @Test
    @DisplayName("GET /simulations/status?status=DRAFT -> OK")
    void shouldGetSimulationsCreatedInTheLastDefaultMinutesAndWithDraftStatus() throws Exception {
        List<Long> expectedResponse = List.of(1L, 2L, 3L, 4L);
        var ids = expectedResponse.stream().map(Long::intValue).toArray();
        SimulationStatusEnum draft = SimulationStatusEnum.DRAFT;

        when(service.getSimulationIdsByStatus(draft, null)).thenReturn(expectedResponse);

        mvc.perform(get(ENDPOINT.concat("/status")).param("status", draft.name()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedResponse.size()))
                .andExpect(jsonPath("$").value(contains(ids)));
    }

    @WithMockUser(roles="SIMULATIONSTATUS_GETSTATUSSIMULATION")
    @Test
    @DisplayName("GET /simulations/status?minutesElapsed=90 -> OK")
    void shouldGetSimulationsCreatedInTheLast90MinutesAndWithDefaultStatus() throws Exception {
        List<Long> expectedResponse = List.of(1L, 2L, 3L, 4L);
        var ids = expectedResponse.stream().map(Long::intValue).toArray();
        long minutesElapsed = 90;

        when(service.getSimulationIdsByStatus(null, minutesElapsed)).thenReturn(expectedResponse);

        mvc.perform(get(ENDPOINT.concat("/status")).param("minutesElapsed", Long.toString(minutesElapsed))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedResponse.size()))
                .andExpect(jsonPath("$").value(contains(ids)));
    }

    @WithMockUser(roles="SIMULATIONSTATUS_UPDATESIMULATIONSSTATUS")
    @Test
    @DisplayName("PUT /simulations/status [1, 2...] -> OK")
    void shouldUpdateSimulationsStatusToDefault() throws Exception {
        List<Long> simulationIds = List.of(1L, 2L, 3L, 4L);
        String requestBody = asJson(simulationIds);

        doNothing().when(service).updateSimulationsStatus(null, simulationIds);

        mvc.perform(put(ENDPOINT.concat("/status")).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(status().isOk());

        verify(service).updateSimulationsStatus(null, simulationIds);
    }

    @WithMockUser(roles="SIMULATIONSTATUS_UPDATESIMULATIONSSTATUS")
    @Test
    @DisplayName("PUT /simulations/status?status=PROCESSING [1, 2...] -> OK")
    void shouldUpdateSimulationsStatusToProcessing() throws Exception {
        List<Long> simulationIds = List.of(1L, 2L, 3L, 4L);
        String requestBody = asJson(simulationIds);
        SimulationStatusEnum processing = SimulationStatusEnum.PROCESSING;

        doNothing().when(service).updateSimulationsStatus(processing, simulationIds);

        mvc.perform(put(ENDPOINT.concat("/status")).param("status", processing.name())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(status().isOk());

        verify(service).updateSimulationsStatus(processing, simulationIds);
    }

    @WithMockUser(roles="SIMULATIONSTATUS_UPDATESIMULATIONSSTATUS")
    @Test
    @DisplayName("PUT /simulations/status [] -> BAD_REQUEST")
    void shouldReturnBadRequestAsNoRequestBodyWasSent() throws Exception {
        mvc.perform(put(ENDPOINT.concat("/status")).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATIONSTATUS_UPDATESIMULATIONSSTATUS")
    @Test
    @DisplayName("PUT /simulations/status [] -> BAD_REQUEST")
    void shouldReturnBadRequestAsNoRequestBodyIsEmptyArray() throws Exception {
        mvc.perform(put(ENDPOINT.concat("/status")).content("[]")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andDo(print());

        verifyZeroInteractions(service);
    }

    @WithMockUser(roles="SIMULATIONSTATUS_GETSIMULATIONSBYSTATUS")
    @Test
    @DisplayName("GET /simulations/{simulationId}/status -> OK ")
    void givenSimulationStatusRequestIsValid_thenReturnValidJson() throws Exception {
        Long simulationId = 67L;
        SimulationStatusResponse expectedStatus;
        expectedStatus = SimulationMocks.mockedSimulationStatusResponse();

        when(service.getSimulationStatus(simulationId)).thenReturn(expectedStatus);

        mvc.perform(get(ENDPOINT.concat("/{simulationId}/status"), simulationId).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(expectedStatus.getId()))
                .andExpect(
                        jsonPath("$.name").value(expectedStatus.getName()))
                .andExpect(jsonPath("$.active").value(expectedStatus.getActive()))
                .andExpect(jsonPath("$.registerDate").value(expectedStatus.getRegisterDate()))
                .andExpect(jsonPath("$.registerOperator").value(expectedStatus.getRegisterOperator()))
                .andExpect(jsonPath("$.updateDate").value(expectedStatus.getUpdateDate()))
                .andExpect(jsonPath("$.updateOperator").value(expectedStatus.getUpdateOperator()));

        verify(service, Mockito.times(1)).getSimulationStatus(simulationId);
    }
}