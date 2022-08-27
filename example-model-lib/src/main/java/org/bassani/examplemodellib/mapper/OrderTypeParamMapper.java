package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.OrderTypeParamEntity;
import br.com.example.purchasesimulatormodellib.domain.response.OrderTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderTypeParamMapper {
    OrderTypeParamMapper INSTANCE = Mappers.getMapper(OrderTypeParamMapper.class);

    static OrderTypeParamMapper orderTypeParameterMapper() {
        return INSTANCE;
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    OrderTypeResponse entityToResponse(OrderTypeParamEntity entity);

}
