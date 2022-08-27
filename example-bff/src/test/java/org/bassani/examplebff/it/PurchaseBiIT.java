package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.PurchaseBiMock;
import org.bassani.examplemodellib.domain.request.PurchaseBiRequest;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import org.bassani.examplemodellib.util.Mocks;
import com.jayway.jsonpath.matchers.JsonPathMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = { "endpoint=" })
class PurchaseBiIT extends IntegrationTestBase {

    private static final String BI_ENDPOINT = "/v1/api/purchases/bi";

	@Test
	@DisplayName("GET /{simulationId}/financial-data -> OK")
	void whenGetFinancialData_thenReturnOK() {
	    //scenario
        List<PurchaseBiResponse> purchaseBiResponses = PurchaseBiMock.mockedListPurchaseSimulatorBiResponse();
        List<Long> productIds = purchaseBiResponses.stream()
                .map(PurchaseBiResponse::getProductCode)
                .collect(Collectors.toList());
        PurchaseBiResponse purchaseBiResponse = PurchaseBiMock.mockedPurchaseSimulatorBiResponse();

        String coreUrlFindProductIdBySimulationId = msCorePath.concat("/simulations/")
            .concat(String.valueOf(1245)).concat("/products?simulationId=1245");
        stubFor(get(coreUrlFindProductIdBySimulationId)
                .willReturn(okJson(asJson(productIds))));

        stubFor(post(BI_ENDPOINT.concat("/financial-data"))
                .willReturn(okJson(Mocks.asJson(purchaseBiResponses))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //action
        var response = rest.getForEntity(thisMsUri.concat("/databi/")
                        .concat(String.valueOf(1245))
                        .concat("/financial-data"), String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        var responseJson = response.getBody();
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].productCode",
                is(purchaseBiResponse.getProductCode().intValue())));
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].branchType",
                is(purchaseBiResponse.getBranchType().intValue())));
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].cmv",
                is(purchaseBiResponse.getCmv().intValue())));
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].stockValue",
                is(purchaseBiResponse.getStockValue().doubleValue())));

	}

    @Test
    @DisplayName("GET /{simulationId}/financial-data -> NotFound")
    void whenGetFinancialData_thenReturnNotFound() {
        //scenario
        List<PurchaseBiResponse> purchaseBiResponses = PurchaseBiMock.mockedListPurchaseSimulatorBiResponse();

        stubFor(post(BI_ENDPOINT.concat("/financial-data"))
                .willReturn(okJson(Mocks.asJson(purchaseBiResponses))));

        PurchaseBiRequest purchaseBiRequest = PurchaseBiMock.mockedPurchaseBiRequest();

        //action
        var response = rest.getForEntity(thisMsUri, String.class);

        //validation
        assertThat(response.getStatusCodeValue(), is(404));

    }

    @Test
    @DisplayName("GET /{simulationId}/chart -> OK")
    void whenGetFinancialChartData_thenReturnOK() {
        //scenario
        List<PurchaseBiChartResponse> purchaseBiResponses = PurchaseBiMock.mockedListPurchaseSimulatorBiChartResponse();
        List<Long> productIds = List.of(123L);
        PurchaseBiChartResponse purchaseBiResponse = PurchaseBiMock.mockedPurchaseSimulatorBiChartResponse();

        String coreUrlFindProductIdBySimulationId = msCorePath.concat("/simulations/")
                .concat(String.valueOf(1245)).concat("/products?simulationId=1245");
        stubFor(get(coreUrlFindProductIdBySimulationId)
                .willReturn(okJson(asJson(productIds))));

        stubFor(post(BI_ENDPOINT.concat("/chart"))
                .willReturn(okJson(Mocks.asJson(purchaseBiResponses))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //action
        var response = rest.getForEntity(thisMsUri.concat("/databi/")
                .concat(String.valueOf(1245))
                .concat("/chart"), String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        var responseJson = response.getBody();
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].month",
                is(purchaseBiResponse.getMonth().toString())));
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].stockDays",
                is(purchaseBiResponse.getStockDays().intValue())));
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].cmv",
                is(purchaseBiResponse.getCmv().intValue())));
        assertThat(responseJson, JsonPathMatchers.hasJsonPath("$[0].stockValue",
                is(purchaseBiResponse.getStockValue().doubleValue())));

    }

    @Test
    @DisplayName("GET /{simulationId}/chart -> NotFound")
    void whenGetFinancialChartData_thenReturnNotFound() {
        //scenario
        List<PurchaseBiChartResponse> purchaseBiChartResponses = PurchaseBiMock.mockedListPurchaseSimulatorBiChartResponse();

        stubFor(post(BI_ENDPOINT.concat("/chart"))
                .willReturn(okJson(Mocks.asJson(purchaseBiChartResponses))));

        PurchaseBiRequest purchaseBiRequest = PurchaseBiMock.mockedPurchaseBiRequest();

        //action
        var response = rest.getForEntity(thisMsUri, String.class);

        //validation
        assertThat(response.getStatusCodeValue(), is(404));

    }
}
