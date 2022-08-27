package org.bassani.examplebff.service;

import org.bassani.examplebff.client.SourceSupplierClient;
import org.bassani.examplebff.mock.ProductSupplierCategoryMock;
import org.bassani.examplebff.mock.ProductSupplierMock;
import org.bassani.examplebff.mock.ProductSupplierSubCategoryMock;
import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierSubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.ProductSupplierResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSupplierServiceTest {

	@Mock
	private SourceSupplierClient repository;

	@InjectMocks
	private ProductSupplierService service;
	
    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(1, 20);
	
    @BeforeAll
    static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("org.bassani.examplebff.mock");
    }
    
	@Test
	@DisplayName("Testing method service.getAllProductBySupplier - Return OK")
	void whenGetProductsBySupplier_WithCompleteRequest_thenReturnOk() {

		String query = "A";
		
		ProductSupplierRequest request = ProductSupplierMock.PRODUCT_SUPPLIER_REQUEST_COMPLETE;

		List<ProductSupplierResponse> expectedResponse = ProductSupplierMock.mockedResponse();

		when(repository.getAllProductBySupplier(request, query, DEFAULT_PAGEABLE)).thenReturn(new PageImpl<>(expectedResponse));

		Page<ProductSupplierResponse> response = new PageImpl<>(expectedResponse);
		
		response = service.getAllProductBySupplier(request, query, DEFAULT_PAGEABLE);
		
		assertThat(response.getContent().size()).isEqualTo(5);
		
		assertIterableEquals(expectedResponse, response);
		
		verifyNoMoreInteractions(repository);

	}

	@Test
    void testGetAllCategoriesBySupplier(){
        //scenario
        Page<CategoryResponse> categoryResponsePage = ProductSupplierCategoryMock.mockedCategoryResponse(DEFAULT_PAGEABLE,
                40);
        Mockito.when(repository.getAllCategories(Mockito.any(ProductSupplierCategoryRequest.class),
                Mockito.any(Pageable.class))).thenReturn(categoryResponsePage);

        //action
        Page<CategoryResponse> allCategories = service.getAllCategories(
                ProductSupplierCategoryRequest.builder().build(), DEFAULT_PAGEABLE);

        //validation
        Assertions.assertTrue(!allCategories.isEmpty());
        Mockito.verify(repository).getAllCategories(Mockito.any(ProductSupplierCategoryRequest.class),
                Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

	@Test
    void testGetAllSubCategoriesBySupplier(){
        //scenario
        Page<SubCategoryResponse> subCategoryResponsePage =
                ProductSupplierSubCategoryMock.mockedSubCategoryResponse(DEFAULT_PAGEABLE,
                40);
        Mockito.when(repository.getAllSubcategories(Mockito.any(ProductSupplierSubCategoryRequest.class),
                Mockito.any(Pageable.class))).thenReturn(subCategoryResponsePage);

        //action
        Page<SubCategoryResponse> allSubCategories = service.getAllSubcategories(
                ProductSupplierSubCategoryRequest.builder().build(), DEFAULT_PAGEABLE);

        //validation
        Assertions.assertTrue(!allSubCategories.isEmpty());
        Mockito.verify(repository).getAllSubcategories(Mockito.any(ProductSupplierSubCategoryRequest.class),
                Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(repository);
    }


}
