package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.ProductEntity;
import br.com.example.purchasesimulatormodellib.domain.projection.ProductDataProjection;
import br.com.example.purchasesimulatormodellib.domain.projection.ProductSupplierProjection;
import br.com.example.purchasesimulatormodellib.domain.response.ProductResponse;
import br.com.example.purchasesimulatormodellib.domain.response.ProductSupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper
//TODO Mesclar com ProductsMapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    static ProductMapper productMapper() { return INSTANCE; }

    ProductResponse entityToResponse(ProductEntity entity);

    List<ProductResponse> entitysToResponses(List<ProductEntity> entity);

    ProductSupplierResponse projectionToResponse(ProductSupplierProjection projection);

    List<ProductResponse> productDataProjectionListToProductResponseList(List<ProductDataProjection> projection);

    default Boolean byteToBoolean(Byte flag) {
        if (Objects.equals(flag, (byte) 1))
            return Boolean.TRUE;
        else if (Objects.equals(flag, (byte) 0))
            return Boolean.FALSE;
        return null;
    }
    
    default ProductResponse projectionDataToResponse(ProductDataProjection projection) {
    		return new ProductResponse(projection.getProductId(),
    									projection.getProductDescription(),
    									projection.getCategoryId(),
    									projection.getSubCategoryId(),
    									projection.getIsInactive().equals(1),
    									projection.getIsKitPromo() == null ? Boolean.FALSE : projection.getIsKitPromo().equals(1),
    									projection.getCommercialDiscountPc(),
    									LocalDate.now(),
    									projection.getProductProviderCode(),
    									projection.getSubGroupId(),
    									projection.getEan(),
    									projection.getCurveFis(),
                                        projection.getTemporaryInactiveCode());
    }
    
    default List<ProductResponse> projectionListToResponseList(List<ProductDataProjection> projectionList) {
    	List<ProductResponse> responseList = new ArrayList<>();
    	for(ProductDataProjection projection : projectionList) {
    		responseList.add(
    				new ProductResponse(projection.getProductId(),
    									projection.getProductDescription(),
    									projection.getCategoryId(),
    									projection.getSubCategoryId(),
    									projection.getIsInactive().equals(1),
    									projection.getIsKitPromo() == null ? Boolean.FALSE : projection.getIsKitPromo().equals(1),
    									projection.getCommercialDiscountPc(),
    									LocalDate.now(),
    									projection.getProductProviderCode(),
    									projection.getSubGroupId(),
    									projection.getEan(),
    									projection.getCurveFis(),
                                        projection.getTemporaryInactiveCode()));
    	}
    	return responseList;
    }
}