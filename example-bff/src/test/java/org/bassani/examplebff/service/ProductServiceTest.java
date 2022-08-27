package org.bassani.examplebff.service;

import org.bassani.examplebff.client.ComposeClient;
import org.bassani.examplebff.client.ProductClient;
import org.bassani.examplebff.mock.ProductMocks;
import org.bassani.examplemodellib.domain.request.CategoryRequest;
import org.bassani.examplemodellib.domain.request.SubCategoryRequest;
import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductClient productClient;
    @Mock
    private ComposeClient compositionClient;

    @InjectMocks
    private ProductService productService;

    @Test
    public void givenCaegorysHasList_whenGetAllIsPerformed_thenAllCategoryAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CategoryResponse> expected = new PageImpl<>(ProductMocks.mockedCategories());
        Page<CategoryResponse> page = new PageImpl<>(expected.getContent());

        when(productClient.getAllCategories(new CategoryRequest(), pageable)).thenReturn(page);
        Page<CategoryResponse> actual = productService.getAllCategories(new CategoryRequest(), pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    public void givenCaegorysHasList_whenGetAllIsPerformed_thenAllCategoryByIdAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CategoryResponse> expected = new PageImpl<>(ProductMocks.mockedCategories()
                .stream()
                .filter(c -> c.getId()
                        .equals(11L))
                .collect(Collectors.toList()));
        Page<CategoryResponse> page = new PageImpl<>(expected.getContent());

        when(productClient.getAllCategories(new CategoryRequest(11L, null), pageable)).thenReturn(page);
        Page<CategoryResponse> actual = productService.getAllCategories(new CategoryRequest(11L, null), pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    public void givenCaegorysHasList_whenGetAllIsPerformed_thenAllCategoryByNamereReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<CategoryResponse> expected = new PageImpl<>(ProductMocks.mockedCategories()
                .stream()
                .filter(c -> c.getName()
                        .equals("GENERICOS"))
                .collect(Collectors.toList()));
        Page<CategoryResponse> page = new PageImpl<>(expected.getContent());

        CategoryRequest request = new CategoryRequest(null, "GENERICOS");
        when(productClient.getAllCategories(request, pageable)).thenReturn(page);
        Page<CategoryResponse> actual = productService.getAllCategories(request, pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    public void givenSubCategorysHasList_whenGetAllIsPerformed_thenAllSubCategoryAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> expected = new PageImpl<>(ProductMocks.mockedSubCategories());
        Page<SubCategoryResponse> page = new PageImpl<>(expected.getContent());

        when(productClient.getAllSubcategories(new SubCategoryRequest(), pageable)).thenReturn(page);
        SubCategoryRequest request = new SubCategoryRequest();
        Page<SubCategoryResponse> actual = productService.getAllSubcategories(request, pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    public void givenSubCategorysHasList_whenGetAllIsPerformed_thenAllSubCategoryByIdAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> expected = new PageImpl<>(ProductMocks.mockedSubCategories()
                .stream()
                .filter(c -> c.getId()
                        .equals(11L))
                .collect(Collectors.toList()));
        Page<SubCategoryResponse> page = new PageImpl<>(expected.getContent());

        SubCategoryRequest request = new SubCategoryRequest(11L, null, null);
        when(productClient.getAllSubcategories(request, pageable)).thenReturn(page);
        Page<SubCategoryResponse> actual = productService.getAllSubcategories(request, pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    public void givenSubCategorysHasList_whenGetAllIsPerformed_thenAllSubCategoryByNamereReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> expected = new PageImpl<>(ProductMocks.mockedSubCategories()
                .stream()
                .filter(c -> c.getName()
                        .equals("GENERICOS"))
                .collect(Collectors.toList()));
        Page<SubCategoryResponse> page = new PageImpl<>(expected.getContent());

        SubCategoryRequest request = new SubCategoryRequest(null, "GENERICOS", null);
        when(productClient.getAllSubcategories(request, pageable)).thenReturn(page);
        Page<SubCategoryResponse> actual = productService.getAllSubcategories(request, pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    public void givenSubCategorysHasList_whenGetAllIsPerformed_thenSubCategoryByCategoryReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<SubCategoryResponse> expected = new PageImpl<>(ProductMocks.mockedSubCategories()
                .stream()
                .filter(c -> c.getCategoryId() == 1L)
                .collect(Collectors.toList()));
        Page<SubCategoryResponse> page = new PageImpl<>(expected.getContent());

        when(compositionClient.getSubcategoriesByCategory(1L, pageable)).thenReturn(page);
        Page<SubCategoryResponse> actual = productService.getAllSubcategoriesByCategory(1L, pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

}
