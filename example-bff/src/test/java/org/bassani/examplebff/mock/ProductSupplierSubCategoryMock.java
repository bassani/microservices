package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.projection.ProductSupplierSubCategoryProjection;
import org.bassani.examplemodellib.domain.request.ProductSupplierSubCategoryRequest;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class ProductSupplierSubCategoryMock implements TemplateLoader {

    public static final ProductSupplierSubCategoryRequest PRODUCT_SUPPLIER_SUB_CATEGORY_REQUEST_COMPLETE = new ProductSupplierSubCategoryRequest(List.of(1L, 2L, 3L), List.of(9L, 10L, 11L));

    public static final String LABEL_PRODUCT_SUPPLIER_SUB_CATEGORY_RESPONSE_COMPLETE = "ProductSupplierSubCategoryResponseComplete";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CATEGORY_ID = "categoryId";

    @Override
    public void load() {
        buildProductSupplierSubCategoryResponseComplete();
    }

    public static List<SubCategoryResponse> mockedResponse() {
        List<SubCategoryResponse> subCategoryResponseList = Fixture
                .from(SubCategoryResponse.class)
                .gimme(5, LABEL_PRODUCT_SUPPLIER_SUB_CATEGORY_RESPONSE_COMPLETE);
        return subCategoryResponseList;
    }

    public static List<ProductSupplierSubCategoryProjection> mockedProjectionFrom(
            List<SubCategoryResponse> responses) {
        return responses.stream().map(ProductSupplierSubCategoryMock::mapperMockProjection).collect(Collectors.toList());
    }

    private static ProductSupplierSubCategoryProjection mapperMockProjection(SubCategoryResponse response) {
        var mock = Mockito.mock(ProductSupplierSubCategoryProjection.class);
        Mockito.when(mock.getCode()).thenReturn(response.getId());
        Mockito.when(mock.getDescription()).thenReturn(response.getName());
        Mockito.when(mock.getCategoryId()).thenReturn(response.getCategoryId());
        return mock;
    }

    private void buildProductSupplierSubCategoryResponseComplete() {

        Fixture.of(SubCategoryResponse.class).addTemplate(LABEL_PRODUCT_SUPPLIER_SUB_CATEGORY_RESPONSE_COMPLETE, new Rule() {
            {
                add(ID, random(Long.class, range(1L, 1000L)));
                add(CATEGORY_ID, random(Long.class, range(1L, 1000L)));
                add(NAME, name());
            }
        });

    }

    public static String mockedSubCategoryResponseAsJson(Pageable pageable, int total) {
        return Mocks.safeWriteValueAsJsonString(mockedSubCategoryResponse(pageable, total));
    }
    public static Page<SubCategoryResponse> mockedSubCategoryResponse(Pageable pageable, int total) {
        IntFunction<SubCategoryResponse> generator = i -> new SubCategoryResponse(Long.valueOf(i), Long.valueOf(i),
                null, String.format("SubCategory 0%d", i));
        return Mocks.mockedResponses(pageable, total, generator);
    }
}
