package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.PurchaseRoundingParamsProjection;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseRoundingParamsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseRoundingParamsMapper {

	PurchaseRoundingParamsMapper INSTANCE = Mappers.getMapper(PurchaseRoundingParamsMapper.class);

    static PurchaseRoundingParamsMapper roundParamMapper() {return INSTANCE;}
    
    PurchaseRoundingParamsResponse projectionToResponse(PurchaseRoundingParamsProjection projection);

}
