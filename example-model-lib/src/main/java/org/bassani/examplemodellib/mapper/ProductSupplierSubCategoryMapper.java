package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.ProductSupplierSubCategoryProjection;
import br.com.example.purchasesimulatormodellib.domain.response.SubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductSupplierSubCategoryMapper {

    ProductSupplierSubCategoryMapper INSTANCE = Mappers.getMapper(ProductSupplierSubCategoryMapper.class);

    static ProductSupplierSubCategoryMapper productSupplierSubCategoryMapper() {
        return INSTANCE;
    }

    @Mapping(source = "code", target = "id")
    @Mapping(source = "description", target = "name")
    @Mapping(source = "categoryId", target = "categoryId")
    SubCategoryResponse projectionToResponse(ProductSupplierSubCategoryProjection projection);

}
