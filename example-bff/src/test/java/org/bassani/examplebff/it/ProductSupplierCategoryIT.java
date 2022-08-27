package org.bassani.examplebff.it;

import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

import static org.bassani.examplebff.mock.ProductSupplierCategoryMock.mockedCategoryResponseAsJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = { "endpoint=/" })
class ProductSupplierCategoryIT extends IntegrationTestBase {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 0;

    @BeforeAll
    static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("org.bassani.examplebff.mock");
    }

	@Test
	@DisplayName("GET /categories?supplierIds=1,2,3 -> OK")
	void whenRequestCategories_thenReturnListOK() {

        //Assumptions
        int pageSize = 4;
        int pageNumber = 3;
        int numberOfElements = 3;
        int total = 15;
        var msParamJson = mockedCategoryResponseAsJson(PageRequest.of(pageNumber, pageSize), total);
        ProductSupplierCategoryRequest request = ProductSupplierCategoryRequest.builder()
                .supplierIds(Arrays.asList(1L))
                .build();

        stubFor(get("/v1/api/purchases/source/supplier/products/categories?page=3&size=4&serialVersionUID=2302806823916764760&supplierIds=1")
                .willReturn(okJson(msParamJson)));

        //Actions
        var response =
                rest.getForEntity(String.format("%s&page=%d&size=%d", thisMsUri.concat("supplier-products/categories?supplierIds=1"),
                        pageNumber, pageSize),
                        String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        var msCompositionJson = response.getBody();
        assertThat(msCompositionJson, hasJsonPath("$.numberOfElements", is(numberOfElements)));
        assertThat(msCompositionJson, hasJsonPath("$.number", is(pageNumber)));
        assertThat(msParamJson, is(msCompositionJson));
	}
}
