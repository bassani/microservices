package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.CriticalProductEntity;
import br.com.example.purchasesimulatormodellib.domain.response.CriticalProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CriticalProductMapper {
    CriticalProductMapper INSTANCE = Mappers.getMapper(CriticalProductMapper.class);

    static CriticalProductMapper criticalProductMapper() { return INSTANCE; }

    CriticalProductResponse entityToResponse(CriticalProductEntity entity);

    CriticalProductEntity responseToEntity(CriticalProductResponse response);

}
