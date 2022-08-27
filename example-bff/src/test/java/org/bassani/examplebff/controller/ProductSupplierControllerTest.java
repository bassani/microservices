package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.ProductSupplierCategoryMock;
import org.bassani.examplebff.mock.ProductSupplierMock;
import org.bassani.examplebff.mock.ProductSupplierSubCategoryMock;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.ProductSupplierService;
import org.bassani.examplemodellib.domain.request.ProductSupplierCategoryRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.domain.request.ProductSupplierSubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.ProductSupplierResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ProductSupplierController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductSupplierControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductSupplierService service;

    @MockBean
    private KeyCloakService keyCloakService;

    private static final String URL = "/purchases/supplier-products";
    private static final String URL_ERROR = "/purchases/product";

    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(1, 20);
    
    private String query = "A";

    @BeforeAll
    static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("org.bassani.examplebff.mock");
    }

    @WithMockUser(roles="PRODUCTSUPPLIER_GETALL")
    @Test
    @DisplayName("POST - /products - OK")
    void whenPOST_return200WithRequestOk() throws Exception {

    	ProductSupplierRequest request = ProductSupplierMock.PRODUCT_SUPPLIER_REQUEST_COMPLETE;
    	   
 		List<ProductSupplierResponse> response = ProductSupplierMock.mockedResponse();
 		
         when(service.getAllProductBySupplier(request, query, DEFAULT_PAGEABLE)).thenReturn(new PageImpl<>(response));

        this.mockMvc.perform(
                post(URL)
                .param("query", query)
                        .content(asJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        
    }

    @WithMockUser(roles="PRODUCTSUPPLIER_GETALL")
    @Test
    @DisplayName("POST - /products - BAD REQUEST")
    void whenPOST_return400WithRequestNullProductSupplier() throws Exception {

       ProductSupplierRequest request =ProductSupplierMock.PRODUCT_SUPPLIER_REQUEST_NULL;
		List<ProductSupplierResponse> response = ProductSupplierMock.mockedResponse();

        when(service.getAllProductBySupplier(request, query, DEFAULT_PAGEABLE)).thenReturn(new PageImpl<>(response));

        this.mockMvc.perform(
                post(URL)
                .param("query", query)
                        .content(asJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles="PRODUCTSUPPLIER_GETALL")
    @Test
    @DisplayName("POST - /products - BAD REQUEST")
    void whenPOST_return400WithProductSupplierRequestSupplierIdNull() throws Exception {


       ProductSupplierRequest request = ProductSupplierMock.PRODUCT_SUPPLIER_REQUEST_SUPPLIER_ID_NULL;
       
		List<ProductSupplierResponse> response = ProductSupplierMock.mockedResponse();

        when(service.getAllProductBySupplier(request, query, DEFAULT_PAGEABLE)).thenReturn(new PageImpl<>(response));

        this.mockMvc.perform(
                post(URL)
                .param("query", query)
                        .content(asJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles="PRODUCTSUPPLIER_GETALL")
    @Test
    @DisplayName("GET - /products - METHOD NOT ALLOWED")
    void whenGET_return405WithMethodNotAllowed() throws Exception {

        this.mockMvc.perform(
                get(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMethodNotAllowed());
    }

    @WithMockUser(roles="PRODUCTSUPPLIER_GETALL")
	@Test
	@DisplayName("POST - /product - NOT FOUND")
	void whenPOST_return404WithRequestIncorrectMapping() throws Exception {
		
		mockMvc.perform(
				post(URL_ERROR)
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isNotFound());
	}


    @WithMockUser(roles="PRODUCTSUPPLIERCATEGORIES_GETALL")
    @Test
    @DisplayName("GET - /supplier-products/categories?supplierIds=1,2,3 - OK")
    void whenPOSTGetCategories_return200WithRequestOk() throws Exception {

        List<CategoryResponse> response = ProductSupplierCategoryMock.mockedResponse();

        when(service.getAllCategories(Mockito.any(ProductSupplierCategoryRequest.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(response));

        this.mockMvc.perform(
                get(URL.concat("/categories"))
                        .param("supplierIds", "1,2,3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles="PRODUCTSUPPLIERSUBCATEGORIES_GETALL")
    @Test
    @DisplayName("GET - /supplier-products/subcategories?supplierIds=1,2,3&categoriesIds=4,5,6 - OK")
    void whenPOSTGetSubCategories_return200WithRequestOk() throws Exception {

        List<SubCategoryResponse> response = ProductSupplierSubCategoryMock.mockedResponse();

        when(service.getAllSubcategories(Mockito.any(ProductSupplierSubCategoryRequest.class),
                Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(response));

        this.mockMvc.perform(
                get(URL.concat("/subcategories"))
                        .param("supplierIds", "1,2,3")
                        .param("categoriesIds", "4,5,6")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    }

}
