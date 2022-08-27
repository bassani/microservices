package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.GeneralAddressEntity;
import br.com.example.purchasesimulatormodellib.domain.response.GeneralAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GeneralAddressMapper {
	GeneralAddressMapper INSTANCE = Mappers.getMapper(GeneralAddressMapper.class);

	static GeneralAddressMapper generalAddressMapper() {
		return INSTANCE;
	}

	GeneralAddressResponse entityToResponse(GeneralAddressEntity entity);

	GeneralAddressEntity responseToEntity(GeneralAddressResponse response);
}