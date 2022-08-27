package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.OperatorEntity;
import br.com.example.purchasesimulatormodellib.domain.response.OperatorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperatorMapper {

    OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    static OperatorMapper operatorMapper() {
        return INSTANCE;
    }

    OperatorResponse entityToResponse(OperatorEntity entity);

    OperatorEntity responseToEntity(OperatorResponse response);

}
