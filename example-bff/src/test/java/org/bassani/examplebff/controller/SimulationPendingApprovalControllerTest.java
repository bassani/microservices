package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationPendingApprovalService;
import org.bassani.examplemodellib.domain.dto.SimulationPendingApprovalDTO;
import org.bassani.examplemodellib.domain.request.SimulationPendingApprovalRequest;
import org.bassani.examplemodellib.domain.response.SimulationPendingApprovalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SimulationPendingApprovalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SimulationPendingApprovalControllerTest {

    private final Integer PAGE = 0;
    private final Integer SIZE = 25;
    private final Sort SORT = Sort.by("date").ascending();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SimulationPendingApprovalService service;

    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="SIMULATIONPENDINGAPPROVAL_POSTFINDALLBYFILTER")
    @Test
    @DisplayName("POST - /simulations-pending-approval - OK")
    void whenPostReturnWithRequestOk() throws Exception {

        SimulationPendingApprovalRequest request =
                SimulationPendingApprovalRequest.builder().initialDate(LocalDate.of(2022, 6, 1))
                        .finalDate(LocalDate.of(2022, 6, 6)).build();

        SimulationPendingApprovalResponse response = SimulationPendingApprovalResponse.builder().simulationId(506L).build();
        List<SimulationPendingApprovalResponse> responses = new ArrayList<SimulationPendingApprovalResponse>();
        responses.add(response);

        doReturn(new PageImpl<>(responses))
                .when(service)
                .findAll(request, PageRequest.of(PAGE, SIZE, SORT));

        mvc.perform(post("/purchases/simulations-pending-approval")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="SIMULATIONPENDINGAPPROVAL_GETALL")
    @Test
    @DisplayName("GET - /simulations-pending-approval - OK")
    void whenGetReturnWithRequestOk() throws Exception {

        List<SimulationPendingApprovalDTO> expected = List.of(
                SimulationPendingApprovalDTO.builder().id(1L).name("BLACK FRIDAY").build(),
                SimulationPendingApprovalDTO.builder().id(7L).name("COLABORATIVA").build());

        doReturn(expected).when(service).getAll();

        mvc.perform(get("/purchases/simulations-pending-approval").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
