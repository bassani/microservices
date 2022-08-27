package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplebff.mock.SimulationOrderSummaryResponseMocks;
import org.bassani.examplebff.mock.SimulationTemplateResponseMocks;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.response.SimulationOrderSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationTemplateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static com.github.tomakehurst.wiremock.client.WireMock.created;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = {"endpoint=/simulations"})
class SimulationIT extends IntegrationTestBase {

    @Test
    @DisplayName("POST /simulations -> OK")
    void givenValidRequest_theReturnValidResponse() {
        SimulationDTO request = SimulationMocks.validSimulationDtoWithGeneralDiscount();
        request.setOperatorId(1234L);
        request.setKeycloakUserId("9669b7c7-4fea-480c-84db-11537e0110b2");
        var requestBody = asJson(request);

        SimulationDTO expectedResponse = SimulationMocks.validSimulationDtoWithGeneralDiscount();
        expectedResponse.setId(1L);
        expectedResponse.setOperatorId(request.getOperatorId());
        var msCoreJson = asJson(expectedResponse);

        stubFor(post(msCorePath).willReturn(
                created().withBody(msCoreJson).withHeader("Content-Type", "application/json;charset=UTF-8")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        var actualResponse = rest.postForEntity(thisMsUri, new HttpEntity<>(requestBody, headers), String.class);

        assertThat(actualResponse.getStatusCode(), is(HttpStatus.CREATED));

        var json = actualResponse.getBody();
        assertThat(json, hasJsonPath("$.id", is(expectedResponse.getId().intValue())));
        assertThat(json, hasJsonPath("$.distributionCenters[0].id",
                is(expectedResponse.getDistributionCenters().get(0).getId().intValue())));
        assertThat(json,
                hasJsonPath("$.suppliers[0].id", is(expectedResponse.getSuppliers().get(0).getId().intValue())));
        assertThat(json,
                hasJsonPath("$.categories[0].id", is(expectedResponse.getCategories().get(0).getId().intValue())));
        assertThat(json, hasJsonPath("$.subcategories[0].id",
                is(expectedResponse.getSubcategories().get(0).getId().intValue())));
        assertThat(json, hasJsonPath("$.subcategories[0].id",
                is(expectedResponse.getSubcategories().get(0).getId().intValue())));
        assertThat(json,
                hasJsonPath("$.classification.id", is(expectedResponse.getClassification().getId().intValue())));
        assertThat(json, hasJsonPath("$.generalDiscount.value",
                is(expectedResponse.getGeneralDiscount().getValue().doubleValue())));
        assertThat(json, hasJsonPath("$.orderType.id", is(expectedResponse.getOrderType().getId().intValue())));
        assertThat(json,
                hasJsonPath("$.productFilter.onlyActive", is(expectedResponse.getProductFilter().getOnlyActive())));
        assertThat(json, hasJsonPath("$.note", is(expectedResponse.getNote())));
        assertThat(json, hasJsonPath("$.simulationType", is(expectedResponse.getSimulationType().getId().intValue())));
        assertThat(json, hasJsonPath("$.newPaymentTerm.newPaymentTermGeneral.newPaymentTermCode",
                is(expectedResponse.getNewPaymentTerm().getNewPaymentTermGeneral().getNewPaymentTermCode().intValue())));
        assertThat(json, hasJsonPath("$.operatorId", is(expectedResponse.getOperatorId().intValue())));
        assertThat(json, hasJsonPath("$.anticipationDate", is(expectedResponse.getAnticipationDate().toString())));

        verify(postRequestedFor(urlEqualTo(msCorePath)).withRequestBody(equalTo(requestBody)));
    }

    @Test
    @DisplayName("GET /simulations/{simulationId}/template -> OK")
    void whenSimulationIdIsValid_thenOkWithSimulationTemplateIsReturned() {
        SimulationTemplateResponse templateResponse = SimulationTemplateResponseMocks.validSimulationTemplateResponse();
        Long simulationId = 890l;
        String coreUrlSimulationTemplate = String.format("%s%d%s", msCorePath.concat("/"), simulationId, "/template");
        stubFor(get(coreUrlSimulationTemplate)
                .willReturn(okJson(asJson(templateResponse))));

        String urlBffSimulationTemplate = String.format("%s%d%s", thisMsUri.concat("/"), simulationId, "/template");
        ResponseEntity<String> response = rest.getForEntity(urlBffSimulationTemplate,
                String.class);

        var json = response.getBody();

        assertThat(json, hasJsonPath("$.purchaseValue", is(templateResponse.getPurchaseValue())));
        assertThat(json, hasJsonPath("$.orderClassification", is(templateResponse.getOrderClassification())));
        assertThat(json, hasJsonPath("$.gain", is(templateResponse.getGain())));
        assertThat(json, hasJsonPath("$.totalBudgetValue", is(templateResponse.getTotalBudgetValue())));
        assertThat(json, hasJsonPath("$.cashFlowImpact", is(templateResponse.getCashFlowImpact())));

    }

    @Test
    @DisplayName("GET /simulations/{simulationId}/order-summary -> OK")
    void whenSimulationIdIsValid_thenOkWithSimulationOrderSummaryIsReturned() {
        SimulationOrderSummaryResponse orderSummaryResponse = SimulationOrderSummaryResponseMocks.validSimulationOrderSummaryResponse();
        Long simulationId = 890l;
        String coreUrlSimulationOrderSummary = String.format("%s%d%s", msCorePath.concat("/"), simulationId, "/order-summary");
        stubFor(get(coreUrlSimulationOrderSummary)
                .willReturn(okJson(asJson(orderSummaryResponse))));

        String urlBffSimulationOrderSummary = String.format("%s%d%s", thisMsUri.concat("/"), simulationId, "/order-summary");
        ResponseEntity<String> response = rest.getForEntity(urlBffSimulationOrderSummary,
                String.class);

        var json = response.getBody();

        assertThat(json, hasJsonPath("$.approvalCompetencyName", is(orderSummaryResponse.getApprovalCompetencyName())));
        assertThat(json, hasJsonPath("$.orderClassification", is(orderSummaryResponse.getOrderClassification())));
        assertThat(json, hasJsonPath("$.purchaseValue", is(orderSummaryResponse.getPurchaseValue())));
        assertThat(json, hasJsonPath("$.parentSupplierName", is(orderSummaryResponse.getParentSupplierName())));
        assertThat(json, hasJsonPath("$.calculationBasisName", is(orderSummaryResponse.getCalculationBasisName())));
        assertThat(json, hasJsonPath("$.regularTermInDaysQuantity", is(orderSummaryResponse.getRegularTermInDaysQuantity().intValue())));
        assertThat(json, hasJsonPath("$.newTermInDaysQuantity", is(orderSummaryResponse.getNewTermInDaysQuantity().intValue())));
        assertThat(json, hasJsonPath("$.deltaTermInDaysQuantity", is(orderSummaryResponse.getDeltaTermInDaysQuantity().intValue())));
        assertThat(json, hasJsonPath("$.regularPaymentDate", is(orderSummaryResponse.getRegularPaymentDate().toString())));
        assertThat(json, hasJsonPath("$.negotiatedPaymentDate", is(orderSummaryResponse.getNegotiatedPaymentDate().toString())));
        assertThat(json, hasJsonPath("$.cashFlowImpact", is(orderSummaryResponse.getCashFlowImpact())));
        assertThat(json, hasJsonPath("$.budgetType", is(orderSummaryResponse.getBudgetType())));
        assertThat(json, hasJsonPath("$.informedBudgetValue", is(orderSummaryResponse.getInformedBudgetValue())));
        assertThat(json, hasJsonPath("$.totalBudgetValue", is(orderSummaryResponse.getTotalBudgetValue())));
        assertThat(json, hasJsonPath("$.gain", is(orderSummaryResponse.getGain())));
        assertThat(json, hasJsonPath("$.budgetReason", is(orderSummaryResponse.getBudgetReason())));
        assertThat(json, hasJsonPath("$.stockDaysPurchase", is(orderSummaryResponse.getStockDaysPurchase())));
        assertThat(json, hasJsonPath("$.dailyFinalStock", is(orderSummaryResponse.getDailyFinalStock())));
        assertThat(json, hasJsonPath("$.dailyCDStock", is(orderSummaryResponse.getDailyCDStock())));
        assertThat(json, hasJsonPath("$.dailyGridStock", is(orderSummaryResponse.getDailyGridStock())));


    }
}
