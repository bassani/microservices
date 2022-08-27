package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.CalculationBasisEntity;
import br.com.example.purchasesimulatormodellib.domain.request.CalculationBasisRequest;
import br.com.example.purchasesimulatormodellib.domain.response.CalculationBasisResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CalculationBasisMapper {

	CalculationBasisMapper INSTANCE = Mappers.getMapper( CalculationBasisMapper.class );

	static CalculationBasisMapper calculationBasisMapper() { return INSTANCE;}
	
	CalculationBasisResponse entityToResponse(CalculationBasisEntity entity);
	
	CalculationBasisEntity requestToEntity(CalculationBasisRequest request);

	List<CalculationBasisEntity> responsesToEntities(List<CalculationBasisResponse> response);
}