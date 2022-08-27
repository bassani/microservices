package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.dto.SimulationDTO;
import br.com.example.purchasesimulatormodellib.domain.request.CategoryRequest;
import br.com.example.purchasesimulatormodellib.domain.request.DataProductsRequest;
import br.com.example.purchasesimulatormodellib.domain.request.DistributionCenterRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SubCategoryRequest;
import br.com.example.purchasesimulatormodellib.domain.request.SupplierRequest;
import br.com.example.purchasesimulatormodellib.domain.request.TotalQuantityProductsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductsMapper {

    ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    static ProductsMapper productsMapper() {
        return INSTANCE;
    }

    @Mapping(target = "distributionCenterIds", source = "distributionCenters")
    @Mapping(target = "supplierIds", source = "suppliers")
    @Mapping(target = "isInactive", source = "productFilter.inactive")
    @Mapping(target = "isKitPromo", source = "productFilter.promoPack")
    @Mapping(source = "categories", target = "categoryIds")
    @Mapping(source = "subcategories", target = "subCategoryIds")
    @Mapping(source = "productFilter.temporaryInactiveCode", target = "temporaryInactiveCode")
    TotalQuantityProductsRequest toTotalProductsRequest(SimulationDTO simulationDataRequest);

    default Long map(CategoryRequest request){
        return request.getId();
    }

    default Long map(SubCategoryRequest request){
        return request.getId();
    }

    default Long map(DistributionCenterRequest request) {
        return request.getId();
    }

    default Long map(SupplierRequest request) {
        return request.getId();
    }

    @Mapping(target = "distributionCenterId", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    @Mapping(target = "isInactive", source = "productFilter.inactive")
    @Mapping(target = "isKitPromo", source = "productFilter.promoPack")
    @Mapping(source = "categories", target = "categoryIds")
    @Mapping(source = "subcategories", target = "subCategoryIds")
    @Mapping(source = "productFilter.temporaryInactiveCode", target = "temporaryInactiveCode")
    DataProductsRequest toDataProductsRequest(SimulationDTO simulationResponse);

}
