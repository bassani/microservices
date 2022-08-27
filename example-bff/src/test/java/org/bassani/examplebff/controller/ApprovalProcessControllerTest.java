package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ApprovalProcessService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.request.SimulationApprovalCompleteRequest;
import org.bassani.examplemodellib.domain.request.SimulationApprovalStartRequest;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceWithVariablesDto;
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

import java.util.HashMap;
import java.util.Map;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ApprovalProcessController.class)
@AutoConfigureMockMvc(addFilters = false)
class ApprovalProcessControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean
    private ApprovalProcessService service;

    @MockBean private KeyCloakService keyCloakService;

    private static final String ENDPOINT = "/purchases/approval";

    @Test
    @WithMockUser(roles="APPROVAL_STARTAPPROVALPROCESS")
    @DisplayName("POST /{simulationId}/start -> OK ")
    void startApprovalProcessInstance() throws Exception {
        Long simulationId = 890L;
        SimulationApprovalStartRequest request =
                SimulationApprovalStartRequest.builder().areaId(1L).operatorId(123L).profileId(1L).build();
        Map<String, VariableValueDto> responseVar = new HashMap<>();
        responseVar.put("ended", null);

        ProcessInstanceWithVariablesDto response = new ProcessInstanceWithVariablesDto();
        response.setVariables(responseVar);

        when(service.startApprovalProcessInstance(anyLong())).thenReturn(response);

        String uri = String.format("%s/%d/start", ENDPOINT, simulationId);

        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJson(request)))
                .andExpect(jsonPath("$.ended").value(false))
                .andExpect(status().isOk());

        verify(service).startApprovalProcessInstance(anyLong());
    }

    @Test
    @WithMockUser(roles="APPROVAL_COMPLETEAPPROVALTASK")
    @DisplayName("POST /{simulationId}/complete -> OK ")
    void completeApprovalTask() throws Exception {
        Long simulationId = 890L;
        SimulationApprovalCompleteRequest request =
                SimulationApprovalCompleteRequest.builder().approved(true).reason("Test approval!").build();

        when(service.completeApprovalTask(anyLong(), any(SimulationApprovalCompleteRequest.class))).thenReturn(true);

        String uri = String.format("%s/%d/complete", ENDPOINT, simulationId);
        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(service).completeApprovalTask(simulationId, request);
    }
}