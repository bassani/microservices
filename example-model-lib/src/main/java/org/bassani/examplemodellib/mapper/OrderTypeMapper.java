package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.OrderTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.response.OrderTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderTypeMapper {

    OrderTypeMapper INSTANCE = Mappers.getMapper(OrderTypeMapper.class);

    static OrderTypeMapper orderTypeMapper(){
        return INSTANCE;
    }

    OrderTypeResponse entityToResponse(OrderTypeEntity entity);

    OrderTypeEntity responseToEntity(OrderTypeResponse response);
}
