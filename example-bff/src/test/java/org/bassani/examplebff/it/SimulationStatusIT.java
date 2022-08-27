package org.bassani.examplebff.it;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = {"endpoint=/simulations/status"})
class SimulationStatusIT extends IntegrationTestBase {

//    @Test - TODO temporariamente comentado
    @DisplayName("GET /simulations/status -> OK")
    void shouldGetSimulationsCreatedInTheLastDefaultMinutesAndWithDefaultStatusByDefault() {
        var expectedResponse = List.of(30, 31, 32).toArray();
        var msCoreJson = asJson(expectedResponse);
        stubFor(get(msCorePath).willReturn(okJson(msCoreJson)));

        var actualResponse = rest.getForEntity(thisMsUri, String.class);

        assertThat(actualResponse.getStatusCode(), is(HttpStatus.OK));

        var json = actualResponse.getBody();
        assertThat(json, hasJsonPath("$.length()", is(expectedResponse.length)));
        assertThat(json, hasJsonPath("$", contains(expectedResponse)));

        verify(getRequestedFor(urlEqualTo(msCorePath)));
    }

    @Test
    @DisplayName("PUT /simulations/status [1, 2...] -> OK")
    void shouldUpdateSimulationsStatusToDefault() {
        var expectedRequest = asJson(List.of(30, 31, 32));
        stubFor(put(msCorePath).willReturn(ok()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        rest.exchange(thisMsUri, HttpMethod.PUT, new HttpEntity<>(expectedRequest, headers), String.class);

        verify(putRequestedFor(urlEqualTo(msCorePath)).withRequestBody(equalToJson(expectedRequest)));
    }
}
