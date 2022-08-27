package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.response.CalculationBasisOptionGroupResponse;
import br.com.example.purchasesimulatormodellib.domain.response.CalculationBasisOptionResponse;
import br.com.example.purchasesimulatormodellib.domain.response.CalculationBasisResponse;
import br.com.example.purchasesimulatormodellib.enums.SalesByPeriodEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CalculationBasisOptionMapper {

	CalculationBasisOptionMapper INSTANCE = Mappers.getMapper( CalculationBasisOptionMapper.class );

	static CalculationBasisOptionMapper calculationBasisOptionMapper() { return INSTANCE;}
	
	@Mapping(target = "parent", constant = "1L")
	CalculationBasisOptionResponse enumToOption(SalesByPeriodEnum salesEnum);

	CalculationBasisOptionGroupResponse responseToOptionGroup(CalculationBasisResponse response,
															  List<CalculationBasisOptionResponse> options);

	List<CalculationBasisResponse> optionGroupsToResponses(List<CalculationBasisOptionGroupResponse> optionGroups);
}