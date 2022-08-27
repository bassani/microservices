package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.OrderItemEntity;
import br.com.example.purchasesimulatormodellib.domain.request.OrderItemRequest;
import br.com.example.purchasesimulatormodellib.domain.response.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    static OrderItemMapper orderItemMapper() {
        return INSTANCE;
    }

    @Mapping(target = "sequenceNumber", source = "id.sequenceNumber")
    @Mapping(target = "orderId", source = "id.orderId")
    OrderItemResponse entityToResponse(OrderItemEntity entity);


    @Mapping(target = "id.sequenceNumber", source = "sequenceNumber")
    @Mapping( target = "id.orderId", source = "orderId")
    OrderItemEntity responseToEntity(OrderItemResponse response);

    @Mapping(target = "id.sequenceNumber", source = "sequenceNumber")
    @Mapping( target = "id.orderId", source = "orderId")
    OrderItemEntity requestToEntity(OrderItemRequest request);

}
