package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.SaleProductEntity;
import br.com.example.purchasesimulatormodellib.domain.projection.SalesHistoricProjection;
import br.com.example.purchasesimulatormodellib.domain.response.SaleProductResponse;
import br.com.example.purchasesimulatormodellib.domain.response.SalesHistoricResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaleProductMapper {

    SaleProductMapper INSTANCE = Mappers.getMapper(SaleProductMapper.class);

    static SaleProductMapper saleProductMapper() { return INSTANCE; }

    SaleProductResponse entityToResponse(SaleProductEntity entity);
    
    SaleProductEntity responseToEntity(SaleProductResponse response);
    
    SalesHistoricResponse projectionToResponse(SalesHistoricProjection entity);

}
