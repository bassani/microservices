package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.CalculationBasisMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = {"endpoint=/calculationBasis"})
class CalculationBasisIT extends IntegrationTestBase {

    @Test
    @DisplayName("GET /calculationBasis -> OK")
    void whenGetWithPageQueryOneIsPerformed_thenSecondPageWithDefaultNumberOfElementsIsReturn() {
        var expectedIds = List.of(1, 2, 3).toArray();
        int expectedSize = 3;
        var msParamJson = CalculationBasisMocks.mockedCalculationBasisAsJson();
        stubFor(get(msCorePath).willReturn(okJson(msParamJson)));

        var response = rest.getForEntity(thisMsUri, String.class);

        assertThat(response.getStatusCodeValue(), is(200));

        var json = response.getBody();
        assertThat(json, hasJsonPath("$.length()", is(expectedSize)));
        assertThat(json, hasJsonPath("$[0:3].id", contains(expectedIds)));
        assertThat(json, hasJsonPath("$[0].keys()", containsInAnyOrder(CalculationBasisMocks.jsonKeys())));
    }
}
