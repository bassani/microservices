package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.PriceRangeValueEntity;
import br.com.example.purchasesimulatormodellib.domain.response.PriceRangeValueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceRangeValueMapper {

    PriceRangeValueMapper INSTANCE = Mappers.getMapper(PriceRangeValueMapper.class);

    static PriceRangeValueMapper priceRangeValueMapper() {return INSTANCE;}

    PriceRangeValueResponse entityToResponse(PriceRangeValueEntity entity);

    PriceRangeValueEntity responseToEntity(PriceRangeValueResponse response);
}
