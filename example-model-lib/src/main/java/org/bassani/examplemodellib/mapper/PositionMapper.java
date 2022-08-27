package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.response.KeyCloakGroupDetailResponse;
import br.com.example.purchasesimulatormodellib.domain.response.PositionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PositionMapper {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    static PositionMapper positionMapper() {
        return INSTANCE;
    }

    PositionResponse keyCloakGroupToResponse(KeyCloakGroupDetailResponse keyCloakGroup);
}
