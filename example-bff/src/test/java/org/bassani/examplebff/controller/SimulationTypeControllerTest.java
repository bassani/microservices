package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.SimulationTypeMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationTypesService;
import org.bassani.examplemodellib.domain.request.SimulationTypesRequestParams;
import org.bassani.examplemodellib.domain.response.SimulationTypesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SimulationTypesController.class)
@AutoConfigureMockMvc(addFilters = false)
class SimulationTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SimulationTypesService simulationTypeService;
    public static final String ENDPOINT = "/purchases/simulationTypes";
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="SIMULATIONTYPES_GETALL")
    @Test
    void shouldFetchAllSimulationTypes() throws Exception {
        List<SimulationTypesResponse> list = SimulationTypeMocks.mockedSimulationTypes();
        SimulationTypesResponse simulationType = list.get(1);

        when(simulationTypeService.getAll(new SimulationTypesRequestParams())).thenReturn(list);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(list.size()))
                .andExpect(jsonPath("$[1].id").value(simulationType.getId()))
                .andExpect(jsonPath("$[1].name").value(simulationType.getName()));
    }

    @WithMockUser(roles="SIMULATIONTYPES_GETALL")
    @Test
    void shouldFetchAllSimulationTypesById() throws Exception {
        List<SimulationTypesResponse> list = SimulationTypeMocks.mockedSimulationTypes().stream().filter(type -> type.getId().equals(1L)).collect(Collectors.toList());

        when(simulationTypeService.getAll(new SimulationTypesRequestParams().builder().id(1L).build())).thenReturn(list);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="SIMULATIONTYPES_GETALL")
    @Test
    void shouldFetchAllSimulationTypesByName() throws Exception {
        List<SimulationTypesResponse> list = SimulationTypeMocks.mockedSimulationTypes().stream().filter(type -> type.getName().equals("ANTECIPAÇÃO")).collect(Collectors.toList());
        when(simulationTypeService.getAll(new SimulationTypesRequestParams().builder().name("ANTECIPAÇÃO").build())).thenReturn(list);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(list.size()));

    }

}
