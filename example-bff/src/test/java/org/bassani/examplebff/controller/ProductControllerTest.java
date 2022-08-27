package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.ProductMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.ProductService;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    public static final String ENDPOINT = "/purchases/products";
    public static final String ENDPOINT_CATEGORY = ENDPOINT + "/categories";
    public static final String ENDPOINT_SUBCATEGORY = ENDPOINT + "/subcategories";

    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="PRODUCTCATEGORIES_GETALL")
    @Test
    void shouldFetchAllCategoryTypes() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedCategories());

        when(productService.getAllCategories(new CategoryRequest(), pageable)).thenReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="PRODUCTCATEGORIES_GETALL")
    @Test
    void shouldFetchAllCategoryTypesById() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedCategories());

        given(productService.getAllCategories(new CategoryRequest(1L, null), pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="PRODUCTCATEGORIES_GETALL")
    @Test
    void shouldFetchAllCategoryTypesByName() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedCategories());

        given(productService.getAllCategories(new CategoryRequest(null, "duto"),
                pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles="PRODUCTSUBCATEGORIES_GETALL")
    @Test
    void shouldFetchAllSubCategoryTypes() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedSubCategories());

        when(productService.getAllSubcategories(new SubCategoryRequest(), pageable)).thenReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_SUBCATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="PRODUCTSUBCATEGORIES_GETALL")
    @Test
    void shouldFetchAllSubCategoryTypesById() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedSubCategories());

        given(productService.getAllSubcategories(new SubCategoryRequest(1L, null, null),
                pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_SUBCATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="PRODUCTSUBCATEGORIES_GETALL")
    @Test
    void shouldFetchAllSubCategoryTypesByName() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedSubCategories());

        given(productService.getAllSubcategories(new SubCategoryRequest(null, "duto", null),
                pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT_SUBCATEGORY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

    }

    @WithMockUser(roles="PRODUCTSUBCATEGORIES_GETBYCATEGORYID")
    @Test
    void shouldFetchAllSubCategoryTypesByCategory() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> pageList = new PageImpl<>(ProductMocks.mockedSubCategories());

        given(productService.getAllSubcategoriesByCategory(111L, pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT.concat("/categories/111/subcategories"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());

    }

}
