package org.bassani.examplebff.it;

import org.bassani.examplebff.mock.ProductSupplierMock;
import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.util.MessageBundle;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;

@TestPropertySource(properties = { "endpoint=/supplier-products" })
class ProductSupplierIT extends IntegrationTestBase {

    @BeforeAll
    static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("org.bassani.examplebff.mock");
    }

	@Test
	@DisplayName("POST /products -> BAD REQUEST")
	void whenRequestSupplierIdNull_thenBadRequestIsReturned() {
		
		ProductSupplierRequest request = ProductSupplierMock.PRODUCT_SUPPLIER_REQUEST_SUPPLIER_ID_NULL;
        stubFor(post(msSupplierPath).willReturn(okJson(asJson(request))));

		ResponseEntity<String> response = rest.postForEntity(thisMsUri, request, String.class);
		
		var json = response.getBody();
		
		assertThat(json, hasJsonPath("$.message", is(MessageBundle.getMessage("validation.failure.message"))));

	}
}
