package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.DistributionCenterSimParamEntity;
import br.com.example.purchasesimulatormodellib.domain.request.DistributionCenterSimulationParameterRequest;
import br.com.example.purchasesimulatormodellib.domain.response.DistributionCenterSimParamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DistributionCenterSimParamMapper {

	DistributionCenterSimParamMapper INSTANCE = Mappers.getMapper(DistributionCenterSimParamMapper.class);

    static DistributionCenterSimParamMapper distributioinCenterSimParamMapper() {return INSTANCE;}
    
//    @Mapping(source = "parameterSimulation.id", target = "parameterSimulation")
//    @Mapping(source = "regionalId", target = "regionalId")
    DistributionCenterSimParamResponse entityToResponse(DistributionCenterSimParamEntity entity);
    
    @Mapping(source = "parameterSimulation.id", target = "parameterSimulation")
//    @Mapping(source = "regionalId", target = "regionalId")
    List<DistributionCenterSimParamResponse> entityToResponse(List<DistributionCenterSimParamEntity> entity);
    
//    @Mapping(target = "parameterSimulation", ignore = true)
//    @Mapping(source = "parameterSimulation", target = "parameterSimulation.id")
//    @Mapping(source = "regionalId", target = "distributionCenterId")
    DistributionCenterSimParamEntity requestToEntity(DistributionCenterSimulationParameterRequest request);
    
    @Mapping(source = "parameterSimulation", target = "parameterSimulation.id")
    @Mapping(source = "regionalId", target = "regionalId")
    List<DistributionCenterSimParamEntity> requestToEntity(List<DistributionCenterSimulationParameterRequest> request);
}
