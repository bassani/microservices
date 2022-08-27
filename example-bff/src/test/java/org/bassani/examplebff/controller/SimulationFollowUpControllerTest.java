package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationFollowUpService;
import org.bassani.examplemodellib.domain.request.SimulationFollowUpRequest;
import org.bassani.examplemodellib.domain.response.SimulationFollowUpResponse;
import org.bassani.examplemodellib.domain.response.SupplierSimParamResponse;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SimulationFollowUpController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SimulationFollowUpControllerTest {
    private final Integer PAGE = 0;
    private final Integer SIZE = 25;
    private final Sort SORT = Sort.by("date").ascending();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SimulationFollowUpService service;
    @MockBean private KeyCloakService keyCloakService;

    SimulationFollowUpResponse response1 = SimulationFollowUpResponse.builder().id(1L).suppliers(List.of(SupplierSimParamResponse.builder().supplierId(1L).build())).orderedQty(1).build();
    SimulationFollowUpResponse response2 = SimulationFollowUpResponse.builder().id(2L).suppliers(List.of(SupplierSimParamResponse.builder().supplierId(2L).build())).orderedQty(2).build();
    SimulationFollowUpResponse response3 = SimulationFollowUpResponse.builder().id(3L).suppliers(List.of(SupplierSimParamResponse.builder().supplierId(3L).build())).orderedQty(3).build();
    SimulationFollowUpResponse response4 = SimulationFollowUpResponse.builder().id(4L).suppliers(List.of(SupplierSimParamResponse.builder().supplierId(4L).build())).orderedQty(4).build();
    SimulationFollowUpResponse response5 = SimulationFollowUpResponse.builder().id(5L).suppliers(List.of(SupplierSimParamResponse.builder().supplierId(5L).build())).orderedQty(5).build();

    List<SimulationFollowUpResponse> responses = new ArrayList<SimulationFollowUpResponse>();

    @WithMockUser(roles="SIMULATIONFOLLOWUP_POSTSIMULATIONFOLLOWUP")
    @Test
    @DisplayName("POST - /simulations-followup - OK")
    void whenPOST_returnWithoutRequestOk() throws Exception {

        responses.add(response1);
        responses.add(response2);
        responses.add(response3);
        responses.add(response4);
        responses.add(response5);

        doReturn(new PageImpl<>(responses)).when(service).getAll(null, PageRequest.of(PAGE, SIZE, SORT));

        mvc.perform(
                        post("/purchases/simulations-followup"))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles="SIMULATIONFOLLOWUP_POSTSIMULATIONFOLLOWUP")
    @Test
    @DisplayName("POST - /simulations-followup - OK")
    void whenPOST_returnWithRequestOk() throws Exception {
        SimulationFollowUpRequest request = SimulationFollowUpRequest.builder().supplierIds(List.of(2L)).build();
        responses.add(response2);

        doReturn(new PageImpl<>(responses))
                .when(service)
                .getAll(request, PageRequest.of(PAGE, SIZE, SORT));

        mvc.perform(
                        post("/purchases/simulations-followup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}
