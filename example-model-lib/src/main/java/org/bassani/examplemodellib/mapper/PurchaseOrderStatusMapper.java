package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.PurchaseOrderStatusEntity;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseOrderStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseOrderStatusMapper {
    PurchaseOrderStatusMapper INSTANCE = Mappers.getMapper(PurchaseOrderStatusMapper.class);

    static PurchaseOrderStatusMapper purchaseOrderStatusMapper() { return INSTANCE; }

    PurchaseOrderStatusResponse entityToResponse(PurchaseOrderStatusEntity entity);

    PurchaseOrderStatusEntity responseToEntity(PurchaseOrderStatusResponse response);
}
