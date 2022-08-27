package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationParametersEntity;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationFollowUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimulationFollowUpMapper {
    SimulationFollowUpMapper INSTANCE = Mappers.getMapper(SimulationFollowUpMapper.class);

    static SimulationFollowUpMapper simulationFollowUpMapper() {
        return INSTANCE;
    }

    SimulationFollowUpResponse entityToResponse(SimulationParametersEntity entity);

    SimulationParametersEntity responseToEntity(SimulationFollowUpResponse response);
}
