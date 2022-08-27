package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.OperatorMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SourceOperatorService;
import org.bassani.examplemodellib.domain.request.OperatorRequest;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SourceOperatorController.class)
class SourceOperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SourceOperatorService sourceOperatorService;
    @MockBean private KeyCloakService keyCloakService;

    public static final String ENDPOINT = "/purchases/operators";

    @WithMockUser(roles="SOURCEOPERATORS_GETALL")
    @Test
    void shouldFetchAllOperators() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<OperatorResponse> pageList = new PageImpl<>(OperatorMocks.mockedOperators());

        when(sourceOperatorService.getAll(new OperatorRequest(), pageable)).thenReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="SOURCEOPERATORS_GETALL")
    @Test
    void shouldFetchAllOperatorsByName() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<OperatorResponse> pageList = new PageImpl<>(OperatorMocks.mockedOperators());

        BDDMockito.given(sourceOperatorService.getAll((new OperatorRequest().builder().name("amazing").build()),
                pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="SOURCEOPERATORS_GETALL")
    @Test
    void shouldFetchAllClassificationTypesByQuery() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<OperatorResponse> pageList = new PageImpl<>(
                OperatorMocks.mockedOperators().stream().filter(c -> c.getName().toLowerCase().contains(
                        "STRONG")).collect(
                        Collectors.toList()));

        BDDMockito.given(sourceOperatorService.getAll(new OperatorRequest().builder().name("strong").build(),
                pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
