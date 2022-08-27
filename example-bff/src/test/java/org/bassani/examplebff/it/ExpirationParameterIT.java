package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.ExpirationParameterMocks;
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

@TestPropertySource(properties = {"endpoint=/expiration-parameters"})
class ExpirationParameterIT extends IntegrationTestBase {

    @Test
    @DisplayName("GET ?latest=1 -> OK")
    void whenGetWithPageQueryOneIsPerformed_thenSecondPageWithDefaultNumberOfElementsIsReturn() {
        var expectedIds = List.of(1, 2).toArray();
        int expectedSize = 2;
        var msCoreJson = ExpirationParameterMocks.mockedExpirationParameterAsJson();
        stubFor(get(msCorePath.concat("?latest=1")).willReturn(okJson(msCoreJson)));

        var response = rest.getForEntity(thisMsUri.concat("/?latest=1"), String.class);

        assertThat(response.getStatusCodeValue(), is(200));

        var json = response.getBody();
        assertThat(json, hasJsonPath("$.length()", is(expectedSize)));
        assertThat(json, hasJsonPath("$[0:2].id", contains(expectedIds)));
        assertThat(json, hasJsonPath("$[0].keys()", containsInAnyOrder(ExpirationParameterMocks.jsonKeys())));
    }
}
