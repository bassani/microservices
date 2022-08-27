package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.DiscountTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.request.DiscountTypeRequest;
import br.com.example.purchasesimulatormodellib.domain.response.DiscountTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiscountTypeMapper {

	DiscountTypeMapper INSTANCE = Mappers.getMapper( DiscountTypeMapper.class );

	static DiscountTypeMapper discountTypeMapper() { return INSTANCE;}
	
	DiscountTypeResponse entityToResponse(DiscountTypeEntity entity);

	DiscountTypeEntity requestToEntity(DiscountTypeRequest request);
}