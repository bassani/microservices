package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SupplierSimParamEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SupplierSimParamRequest;
import br.com.example.purchasesimulatormodellib.domain.response.SupplierSimParamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SupplierParameterSimulationMapper {
	
	SupplierParameterSimulationMapper INSTANCE = Mappers.getMapper( SupplierParameterSimulationMapper.class );

	static SupplierParameterSimulationMapper supplierParameterSimulationMapper() { return INSTANCE;}

	SupplierSimParamResponse entityToResponse(SupplierSimParamEntity entity);

    @Mapping(source = "id", target = "supplierId")
	List<SupplierSimParamResponse> entityToResponse(List<SupplierSimParamEntity> entity);
	
	SupplierSimParamEntity requestToEntity(SupplierSimParamRequest request);
	
	List<SupplierSimParamEntity> requestToEntity(List<SupplierSimParamRequest> request);
}
