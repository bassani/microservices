package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.CategorySimParamEntity;
import br.com.example.purchasesimulatormodellib.domain.request.CategorySimParamRequest;
import br.com.example.purchasesimulatormodellib.domain.response.CategorySimParamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryParameterSimulationMapper {

	CategoryParameterSimulationMapper INSTANCE = Mappers.getMapper(CategoryParameterSimulationMapper.class);

    static CategoryParameterSimulationMapper categoryParameterSimulationMapper() {return INSTANCE;}

    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "id", target = "categoryId")
    CategorySimParamResponse entityToResponse(CategorySimParamEntity entity);

    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "id", target = "categoryId")
    List<CategorySimParamResponse> entityToResponse(List<CategorySimParamEntity> entity);
    
    CategorySimParamEntity requestToEntity(CategorySimParamRequest request);
    
    List<CategorySimParamEntity> requestToEntity(List<CategorySimParamRequest> request);
}
