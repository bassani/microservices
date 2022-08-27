package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.PurchaseBiMock;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.PurchaseBiService;
import org.bassani.examplemodellib.domain.request.PurchaseBiRequest;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import org.bassani.examplemodellib.util.Mocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PurchaseBiController.class)
@AutoConfigureMockMvc(addFilters = false)
class BiControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PurchaseBiService service;

    @MockBean private KeyCloakService keyCloakService;

    private static final String ENDPOINT = "/purchases/databi/";

	@Test
	@DisplayName("GET - /{simulationId}/financial-data - OK")
	void whenGET_returnOk() throws Exception {

        List<PurchaseBiResponse> purchaseBiResponse = PurchaseBiMock.mockedListPurchaseSimulatorBiResponse();
        PurchaseBiRequest purchaseBiRequest = PurchaseBiMock.mockedPurchaseBiRequest();
        when(service.getFinancialData(Mockito.anyLong())).thenReturn(purchaseBiResponse);
		String jsonRequest = Mocks.asJson(purchaseBiRequest);
        mvc.perform(get(ENDPOINT.concat(String.valueOf(Mockito.anyLong()))
                        .concat("/financial-data")))
                .andExpect(status().isOk());
		
	}

    @Test
    @DisplayName("GET - /{simulationId}/chart - OK")
    void whenGETChart_returnOk() throws Exception {

        List<PurchaseBiChartResponse> purchaseBiChartResponse = PurchaseBiMock.mockedListPurchaseSimulatorBiChartResponse();
        PurchaseBiRequest purchaseBiRequest = PurchaseBiMock.mockedPurchaseBiRequest();
        when(service.getFinancialChartData(Mockito.anyLong())).thenReturn(purchaseBiChartResponse);
        String jsonRequest = Mocks.asJson(purchaseBiRequest);
        mvc.perform(get(ENDPOINT.concat(String.valueOf(Mockito.anyLong()))
                        .concat("/chart")))
                .andExpect(status().isOk());

    }
}