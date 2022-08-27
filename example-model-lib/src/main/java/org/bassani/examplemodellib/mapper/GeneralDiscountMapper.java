package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.GeneralDiscountEntity;
import br.com.example.purchasesimulatormodellib.domain.request.GeneralDiscountRequest;
import br.com.example.purchasesimulatormodellib.domain.response.GeneralDiscountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneralDiscountMapper {

    GeneralDiscountMapper INSTANCE = Mappers.getMapper(GeneralDiscountMapper.class);

    static GeneralDiscountMapper generalDiscountMapper() {return INSTANCE;}
    
    @Mapping(target = "parameterSimulationId", source = "simulationParameters.id")
    @Mapping(target = "discountTypeId", source = "type.id")
    GeneralDiscountResponse toResponse(GeneralDiscountEntity entity);
    
    @Mapping(target = "type.id", source = "discountTypeId")
    @Mapping(target = "simulationParameters.id", source = "parameterSimulationId")
    GeneralDiscountEntity toEntity(GeneralDiscountRequest request);
}
