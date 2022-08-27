package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ProductDiscountEntity;
import br.com.example.purchasesimulatormodellib.domain.request.ProductDiscountSimParamRequest;
import br.com.example.purchasesimulatormodellib.domain.response.ProductDiscountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductDiscountMapper {

	ProductDiscountMapper INSTANCE = Mappers.getMapper(ProductDiscountMapper.class);

    static ProductDiscountMapper productDiscountMapper() {return INSTANCE;}
    
    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "id", target = "produtoId")
    @Mapping(source = "type.id", target = "discountType")
    ProductDiscountResponse toResponse(ProductDiscountEntity entity);

    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "id", target = "produtoId")
    @Mapping(source = "type.id", target = "discountType")
    List<ProductDiscountResponse> toResponse(List<ProductDiscountEntity> entity);
    
//    @Mapping(source = "parameterSimulation", target = "parameterSimulation.id")
//    @Mapping(source = "produtoId", target = "produtoId")
    @Mapping(source = "discountType", target = "type.id")
    ProductDiscountEntity requestToEntity(ProductDiscountSimParamRequest request);
    
//    @Mapping(source = "parameterSimulation", target = "parameterSimulation.id")
//    @Mapping(source = "produtoId", target = "produtoId")
    @Mapping(source = "discountType", target = "discountType.id")
    List<ProductDiscountEntity> requestToEntity(List<ProductDiscountSimParamRequest> request);
}
