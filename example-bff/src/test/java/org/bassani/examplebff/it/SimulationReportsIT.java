package org.bassani.examplebff.it;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = {"endpoint=/simulations"})
class SimulationReportsIT extends IntegrationTestBase {

    @Test
    @DisplayName("GET /{id}/download -> OK")
    void shouldCheckByteArraySize() throws IOException {
        Long simulationId = 1L;

        byte[] workbookAsByteArray = {1, 2, 3, 4, 5};

        String simulationsPath = String.format("%s/%d/export", msReportsPath, simulationId);
        stubFor(get(simulationsPath).willReturn(ok().withBody(workbookAsByteArray)));

        String uri = String.format("%s/%d/download", thisMsUri, simulationId);
        var actualResponse = rest.getForEntity(uri, Resource.class);

        assertThat(actualResponse.getStatusCode(), is(HttpStatus.OK));

        Resource actualBody = actualResponse.getBody();
        assertNotNull(actualBody);

        InputStream inputStream = actualBody.getInputStream();
        byte[] responseBody = inputStream.readAllBytes();
        assertTrue(responseBody.length > 0);
    }
}
