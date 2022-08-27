package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationStatusEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SimulationStatusRequest;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationStatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SimulationStatusMapper {

	SimulationStatusMapper INSTANCE = Mappers.getMapper( SimulationStatusMapper.class );

	static SimulationStatusMapper simulationStatusMapper() { return INSTANCE;}
	
	SimulationStatusResponse entityToResponse(SimulationStatusEntity entity);
	
	SimulationStatusEntity requestToEntity(SimulationStatusRequest request);

	List<SimulationStatusEntity> responsesToEntities(List<SimulationStatusResponse> response);
}