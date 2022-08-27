package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.CategoryResponse;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;

import java.util.List;

public class ProductMocks {

    public static List<CategoryResponse> mockedCategories(){
        return List.of(
                CategoryResponse.builder().id(1L).name("BCATEGORIA A").masterCategoryId(21L).build(),
                CategoryResponse.builder().id(111L).name("PSICOTROPICOS").masterCategoryId(19L).build(),
                CategoryResponse.builder().id(114L).name("PRODUTOS DE GELADEIRA").masterCategoryId(1L).build(),
                CategoryResponse.builder().id(116L).name("PRODUTOS PARA ENCOMENDA").masterCategoryId(1L).build(),
                CategoryResponse.builder().id(201L).name("GENERICOS").masterCategoryId(1L).build(),
                CategoryResponse.builder().id(2011L).name("RX - LETRA (A)").masterCategoryId(1L).build(),
                CategoryResponse.builder().id(221L).name("RX - LETRA (B)").masterCategoryId(1L).build()
        );
    }

    public static List<SubCategoryResponse> mockedSubCategories(){
        return List.of(
                SubCategoryResponse.builder().id(1L).name("BCATEGORIA A").categoryId(21L).build(),
                SubCategoryResponse.builder().id(111L).name("PSICOTROPICOS").categoryId(19L).build(),
                SubCategoryResponse.builder().id(114L).name("PRODUTOS DE GELADEIRA").categoryId(1L).build(),
                SubCategoryResponse.builder().id(116L).name("PRODUTOS PARA ENCOMENDA").categoryId(1L).build(),
                SubCategoryResponse.builder().id(201L).name("GENERICOS").categoryId(1L).build(),
                SubCategoryResponse.builder().id(2011L).name("RX - LETRA (A)").categoryId(1L).build(),
                SubCategoryResponse.builder().id(221L).name("RX - LETRA (B)").categoryId(1L).build()
        );
    }
}
