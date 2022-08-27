package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

@TestPropertySource(properties = {"endpoint=/simulations"})
class SimulationErrorWatcherIT extends IntegrationTestBase {

/*    @Test
    @DisplayName("POST composition/simulation/ -> SERVER_ERROR then PUT /simulation/{id}/status -> OK")
    void givenMsCompositionUnavailable_whenSimulationIsTriggered_thenUpdateSimulationStatusToError() {
        var simId = 1L;

        SimulationDTO simulationDTO = SimulationMocks.mockedSimulationParametersResponse(simId);
        stubFor(post(msCorePath).willReturn(
                created().withBody(asJson(simulationDTO)).withHeader("Content-Type", "application/json")));
        stubFor(post(msCompositionPath).willReturn(serverError().withHeader("Content-Type", "application/json")));

        String msCoreSimulationStatusEndpoint = String.format("/v1/api/purchases/core/simulations/%d/status", simId);
        stubFor(put(msCoreSimulationStatusEndpoint).willReturn(okJson("true")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        rest.postForEntity(thisMsUri, new HttpEntity<>(simulationDTO, headers), String.class);

        verify(postRequestedFor(urlPathEqualTo(msCorePath)));
        verify(postRequestedFor(urlPathEqualTo(msCompositionPath)));
        verify(putRequestedFor(urlPathEqualTo(msCoreSimulationStatusEndpoint)));
    }*/

    @Test
    @DisplayName("POST core/simulation/ -> SERVER_ERROR then NOOP")
    void givenMsCompositionUnavailable_whenSimulationIsTriggered_thenUpdateSimulationStatusToError2() {
        SimulationDTO simulationDTO = SimulationMocks.validSimulationDtoWithGeneralDiscount();
        stubFor(post(msCorePath).willReturn(serverError().withHeader("Content-Type", "application/json")));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        rest.postForEntity(thisMsUri, new HttpEntity<>(simulationDTO, headers), String.class);

        verify(postRequestedFor(urlPathEqualTo(msCorePath)));
        verify(0, postRequestedFor(urlPathEqualTo(msCompositionPath)));

        String msCoreSimulationStatusEndpoint = "/v1/api/purchases/core/simulations/(\\d+)/status";
        verify(0, putRequestedFor(urlPathMatching(msCoreSimulationStatusEndpoint)));
    }
}
