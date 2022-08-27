package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ValueTypeSimulationEntity;
import br.com.example.purchasesimulatormodellib.domain.request.ValueTypeSimulationRequest;
import br.com.example.purchasesimulatormodellib.domain.response.ValueTypeSimulationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ValueTypeSimulationMapper {

	ValueTypeSimulationMapper INSTANCE = Mappers.getMapper(ValueTypeSimulationMapper.class);

    static ValueTypeSimulationMapper valueSimulationMapper() {return INSTANCE;}
    
    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    ValueTypeSimulationResponse entityToResponse(ValueTypeSimulationEntity entity);
    
    @Mapping(source = "parameterSimulation", target = "simulationParameters.id")
    ValueTypeSimulationEntity requestToEntity(ValueTypeSimulationRequest request);
}
