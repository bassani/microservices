package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.ProductCostEntity;
import br.com.example.purchasesimulatormodellib.domain.response.ProductCostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCostMapper {

    ProductCostMapper INSTANCE = Mappers.getMapper(ProductCostMapper.class);

    static ProductCostMapper productCostMapper() { return INSTANCE; }

    ProductCostResponse entityToResponse(ProductCostEntity entity);

    ProductCostEntity responseToEntity(ProductCostResponse response);
}
