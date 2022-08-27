package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.projection.ProductSupplierCategoryProjection;
import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class ProductSupplierCategoryMock implements TemplateLoader {

    public static final ProductSupplierCategoryRequest PRODUCT_SUPPLIER_CATEGORY_REQUEST_COMPLETE = new ProductSupplierCategoryRequest(List.of(1L, 2L, 3L));

    public static final String LABEL_PRODUCT_SUPPLIER_CATEGORY_RESPONSE_COMPLETE = "ProductSupplierCategoryResponseComplete";

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public void load() {
        buildProductSupplierCategoryResponseComplete();
    }

    public static List<CategoryResponse> mockedResponse() {
        List<CategoryResponse> categoryResponseList = Fixture
                .from(CategoryResponse.class)
                .gimme(5, LABEL_PRODUCT_SUPPLIER_CATEGORY_RESPONSE_COMPLETE);
        return categoryResponseList;
    }

    public static List<ProductSupplierCategoryProjection> mockedProjectionFrom(
            List<CategoryResponse> responses) {
        return responses.stream().map(ProductSupplierCategoryMock::mapperMockProjection).collect(Collectors.toList());
    }

    private static ProductSupplierCategoryProjection mapperMockProjection(CategoryResponse response) {
        var mock = Mockito.mock(ProductSupplierCategoryProjection.class);
        Mockito.when(mock.getCode()).thenReturn(response.getId());
        Mockito.when(mock.getDescription()).thenReturn(response.getName());
        return mock;
    }

    private void buildProductSupplierCategoryResponseComplete() {

        Fixture.of(CategoryResponse.class).addTemplate(LABEL_PRODUCT_SUPPLIER_CATEGORY_RESPONSE_COMPLETE, new Rule() {
            {
                add(ID, random(Long.class, range(1L, 1000L)));
                add(NAME, name());
            }
        });

    }
    public static String mockedCategoryResponseAsJson(Pageable pageable, int total) {
        return Mocks.safeWriteValueAsJsonString(mockedCategoryResponse(pageable, total));
    }
    public static Page<CategoryResponse> mockedCategoryResponse(Pageable pageable, int total) {
        IntFunction<CategoryResponse> generator = i -> new CategoryResponse(Long.valueOf(i), null,
                String.format("Category 0%d", i));
        return Mocks.mockedResponses(pageable, total, generator);
    }
}
