package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SubCategorySimParamEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SubCategorySimParamRequest;
import br.com.example.purchasesimulatormodellib.domain.response.SubCategorySimParamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubCategoryParameterSimulationMapper {

	SubCategoryParameterSimulationMapper INSTANCE = Mappers.getMapper(SubCategoryParameterSimulationMapper.class);

    static SubCategoryParameterSimulationMapper subCategoryParameterSimulationMapper() {return INSTANCE;}

    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "id", target = "subCategoryId")
    SubCategorySimParamResponse entityToResponse(SubCategorySimParamEntity entity);

    @Mapping(source = "simulationParameters.id", target = "parameterSimulation")
    @Mapping(source = "id", target = "subCategoryId")
    List<SubCategorySimParamResponse> entityToResponse(List<SubCategorySimParamEntity> entity);

    SubCategorySimParamEntity requestToEntity(SubCategorySimParamRequest request);
    
    List<SubCategorySimParamEntity> requestToEntity(List<SubCategorySimParamRequest> request);
}
