package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SimulationTypeRequest;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimulationTypeMapper {

	SimulationTypeMapper INSTANCE = Mappers.getMapper( SimulationTypeMapper.class );

	static SimulationTypeMapper simulationTypeMapper() { return INSTANCE;}
	
	SimulationTypeResponse entityToResponse(SimulationTypeEntity entity);

	SimulationTypeEntity requestToEntity(SimulationTypeRequest request);
}