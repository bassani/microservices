package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.QuantityTypeSimulationEntity;
import br.com.example.purchasesimulatormodellib.domain.request.QuantityTypeSimulationRequest;
import br.com.example.purchasesimulatormodellib.domain.response.QuantityTypeSimulationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuantityTypeSimulationMapper {

	QuantityTypeSimulationMapper INSTANCE = Mappers.getMapper(QuantityTypeSimulationMapper.class);

    static QuantityTypeSimulationMapper quantityTypeSimulationMapper() {return INSTANCE;}
    
    @Mapping(source = "parameterSimulation.id", target = "parameterSimulation")
    @Mapping(source = "produtoId", target = "produtoId")
    QuantityTypeSimulationResponse entityToResponse(QuantityTypeSimulationEntity entity);
    
    @Mapping(source = "parameterSimulation", target = "parameterSimulation.id")
    @Mapping(source = "produtoId", target = "produtoId")
    QuantityTypeSimulationEntity requestToEntity(QuantityTypeSimulationRequest request);
}
