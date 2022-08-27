package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.client.PurchaseBiClient;
import org.bassani.examplebff.mock.PurchaseBiMock;
import org.bassani.examplemodellib.domain.request.PurchaseBiRequest;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BiServiceTest {

	@Mock
	private PurchaseBiClient biClient;

    @Mock
    private CoreClient coreClient;

	@InjectMocks
	private PurchaseBiService biService;

	@Test
	void shallReturnFinancialData() {
	    //scenario
		List<PurchaseBiResponse> purchaseBiResponseExpected =
                PurchaseBiMock.mockedListPurchaseSimulatorBiResponse();
        PurchaseBiRequest purchaseBiRequest = PurchaseBiMock.mockedPurchaseBiRequest();

        when(coreClient.findProductIdBySimulationId(Mockito.anyLong())).thenReturn(purchaseBiRequest.getProductIds());
        when(biClient.getFinancialData(purchaseBiRequest)).thenReturn(purchaseBiResponseExpected);

		//action
        List<PurchaseBiResponse> purchaseBiResponse = biService.getFinancialData(Mockito.anyLong());

        //validation
		assertEquals(purchaseBiResponseExpected, purchaseBiResponse);
	}

    @Test
    void shallReturnEmptyList() {
        //scenario
        when(coreClient.findProductIdBySimulationId(Mockito.anyLong())).thenReturn(null);

        //action
        List<PurchaseBiResponse> purchaseBiResponse = biService.getFinancialData(Mockito.anyLong());

        //validation
        assertEquals(new ArrayList<>(), purchaseBiResponse);
    }

    @Test
    void shallReturnEmptyListProducts() {
        //scenario
        when(coreClient.findProductIdBySimulationId(Mockito.anyLong())).thenReturn(null);

        //action
        List<Long> productIdsActual = biService.findProductIdBySimulationId(Mockito.anyLong());

        //validation
        assertNull(productIdsActual);
    }

    @Test
    void shallReturnFinancialChartData() {
        //scenario
        List<PurchaseBiChartResponse> purchaseBiChartResponseExpected =
                PurchaseBiMock.mockedListPurchaseSimulatorBiChartResponse();
        PurchaseBiRequest purchaseBiRequest = PurchaseBiMock.mockedPurchaseBiRequest();

        when(coreClient.findProductIdBySimulationId(Mockito.anyLong())).thenReturn(purchaseBiRequest.getProductIds());
        when(biClient.getFinancialChartData(purchaseBiRequest)).thenReturn(purchaseBiChartResponseExpected);

        //action
        List<PurchaseBiChartResponse> purchaseBiChartResponse = biService.getFinancialChartData(Mockito.anyLong());

        //validation
        assertEquals(purchaseBiChartResponseExpected, purchaseBiChartResponse);
    }

    @Test
    void shallChartReturnEmptyList() {
        //scenario
        when(coreClient.findProductIdBySimulationId(Mockito.anyLong())).thenReturn(null);

        //action
        List<PurchaseBiChartResponse> purchaseBiResponse = biService.getFinancialChartData(Mockito.anyLong());

        //validation
        assertEquals(new ArrayList<>(), purchaseBiResponse);
    }
}