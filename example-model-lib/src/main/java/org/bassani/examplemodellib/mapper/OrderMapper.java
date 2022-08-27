package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.OrderEntity;
import br.com.example.purchasesimulatormodellib.domain.request.OrderRequest;
import br.com.example.purchasesimulatormodellib.domain.response.OrderParamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = OrderItemMapper.class)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    static OrderMapper orderMapper() {
        return INSTANCE;
    }

    OrderParamResponse entityToResponse(OrderEntity entity);

    OrderEntity responseToEntity(OrderParamResponse response);

    OrderEntity requestToEntity(OrderRequest request);

}
