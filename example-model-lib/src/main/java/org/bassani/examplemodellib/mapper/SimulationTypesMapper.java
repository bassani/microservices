package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.response.SimulationTypesResponse;
import br.com.example.purchasesimulatormodellib.enums.SimulationTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimulationTypesMapper {

    SimulationTypesMapper INSTANCE = Mappers.getMapper(SimulationTypesMapper.class);

    static SimulationTypesMapper simulationTypesMapper() { return INSTANCE; }

    SimulationTypesResponse enumToResponse(SimulationTypeEnum type);
}
