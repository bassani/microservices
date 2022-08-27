package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.ProductSupplierCategoryProjection;
import br.com.example.purchasesimulatormodellib.domain.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductSupplierCategoryMapper {

    ProductSupplierCategoryMapper INSTANCE = Mappers.getMapper(ProductSupplierCategoryMapper.class);

    static ProductSupplierCategoryMapper productSupplierCategoryMapper() {
        return INSTANCE;
    }

    @Mapping(source = "code", target = "id")
    @Mapping(source = "description", target = "name")
    CategoryResponse projectionToResponse(ProductSupplierCategoryProjection projection);

}
