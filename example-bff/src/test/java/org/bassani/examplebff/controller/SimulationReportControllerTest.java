package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.SimulationReportService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SimulationReportController.class)
@AutoConfigureMockMvc(addFilters = false)
class SimulationReportControllerTest {

    private static final String URL = "/purchases/simulations/";
    private static final String URL_ERROR = "/purchases/simulation/downloads/";

    @Autowired private MockMvc mvc;
    @MockBean private SimulationReportService service;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="SIMULATIONREPORT_DOWNLOAD")
    @Test
    @DisplayName("GET /{id}/download/ -> OK ")
    void givenRequestIsValid_thenReturnOK() throws Exception {
        Long simulationId = 2L;

        when(service.getSpreadsheet(simulationId)).thenReturn(null);

        mvc.perform(get(URL.concat(String.valueOf(simulationId).concat("/download")))
        		.contentType(MediaType.APPLICATION_OCTET_STREAM)
        		.accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));

        verify(service).getSpreadsheet(simulationId);
    }

    @WithMockUser(roles="SIMULATIONREPORT_DOWNLOAD")
	@Test
	@DisplayName("GET - /simulations/download/ - NOT FOUND")
	void givenRequestIncorrectMapping_return404NotFound() throws Exception {
		
        Long simulationId = 3L;

        when(service.getSpreadsheet(simulationId)).thenReturn(null);
        
		mvc.perform(
				get(URL_ERROR.concat(String.valueOf(simulationId)))
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
        		.accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isNotFound());
	}

    @WithMockUser(roles="SIMULATIONREPORT_DOWNLOAD")
    @Test
    @DisplayName("POST - /{id}/download/ - METHOD NOT ALLOWED")
    void givenPostMethod_return405MethodNotAllowed() throws Exception {

        Long simulationId = 4L;

        when(service.getSpreadsheet(simulationId)).thenReturn(null);
        
    	mvc.perform(
                post(URL.concat(String.valueOf(simulationId).concat("/download")))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
        		.accept(MediaType.APPLICATION_OCTET_STREAM))
        		.andExpect(status().isMethodNotAllowed());
    }
   
}