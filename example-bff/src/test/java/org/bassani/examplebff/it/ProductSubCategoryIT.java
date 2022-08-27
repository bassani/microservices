package org.bassani.examplebff.it;

import org.bassani.examplebff.client.ProductClient;
import org.bassani.examplebff.mock.ProductSupplierSubCategoryMock;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@TestPropertySource(properties = { "endpoint=/" })
class ProductSubCategoryIT extends IntegrationTestBase {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 0;

    @Autowired
    ProductClient productClient;

    @BeforeAll
    static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("org.bassani.examplebff.mock");
    }

	@Test
	@DisplayName("GET /subCategories(sorted=name,asc) -> OK")
	void whenRequestSubCategories_thenReturnListOK() {
        //Assumptions
        int pageSize = 4;
        int pageNumber = 3;
        int numberOfElements = 10;
        int total = 15;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        var msParamJson = ProductSupplierSubCategoryMock.mockedSubCategoryResponseAsJson(PageRequest.of(pageNumber,
                pageSize), total);

        SubCategoryRequest req = new SubCategoryRequest();
        req.setId(null);

        Page<SubCategoryResponse> subCategoryResponses = productClient.getAllSubcategories(req, pageRequest);

        stubFor(get("/v1/api/purchases/source/products/subcategories")
                .willReturn(okJson(msParamJson)));

        //Actions
        var response =
                rest.getForEntity(String.format("%s?page=%d&size=%d%s", thisMsUri.concat("products/subcategories"),
                        pageNumber, pageSize, "sort=name,asc"),
                        String.class);

        //Verifications
        assertThat(response.getStatusCodeValue(), is(200));
        var msCompositionJson = response.getBody();
        assertThat(msCompositionJson, hasJsonPath("$.numberOfElements", is(numberOfElements)));
        assertThat(msCompositionJson, hasJsonPath("$.number", is(pageNumber)));
        assertThat(msCompositionJson, hasJsonPath("$.sort['sorted']", is(true)));
	}
}
