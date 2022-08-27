package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.PurchaseOrderTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseOrderTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseOrderTypeMapper {
	PurchaseOrderTypeMapper INSTANCE = Mappers.getMapper(PurchaseOrderTypeMapper.class);

    static PurchaseOrderTypeMapper purchaseOrderTypeMapper() { return INSTANCE; }

    PurchaseOrderTypeResponse entityToResponse(PurchaseOrderTypeEntity entity);

    PurchaseOrderTypeEntity responseToEntity(PurchaseOrderTypeResponse response);

}
