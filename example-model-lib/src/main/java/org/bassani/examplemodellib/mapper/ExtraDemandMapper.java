package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.ExtraDemandProjection;
import br.com.example.purchasesimulatormodellib.domain.response.ExtraDemandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExtraDemandMapper {

    ExtraDemandMapper INSTANCE = Mappers.getMapper(ExtraDemandMapper.class);

    static ExtraDemandMapper extraDemandMapper(){
        return INSTANCE;
    }

    ExtraDemandResponse projectionToResponse(ExtraDemandProjection projection);
}
