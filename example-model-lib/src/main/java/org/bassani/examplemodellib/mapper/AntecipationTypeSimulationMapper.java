package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.AnticipationTypeSimulationEntity;
import br.com.example.purchasesimulatormodellib.domain.request.AntecipationTypeSimulationRequest;
import br.com.example.purchasesimulatormodellib.domain.response.AntecipationTypeSimulationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AntecipationTypeSimulationMapper {

	AntecipationTypeSimulationMapper INSTANCE = Mappers.getMapper(AntecipationTypeSimulationMapper.class);

    static AntecipationTypeSimulationMapper antecipationTypeSimulationMapper() {return INSTANCE;}

    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "anticipationDate", target = "antecipation")
    AntecipationTypeSimulationResponse entityToResponse(AnticipationTypeSimulationEntity entity);
    
    AnticipationTypeSimulationEntity requestToEntity(AntecipationTypeSimulationRequest request);
}
