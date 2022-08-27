package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.OperatorSupplierEntity;
import br.com.example.purchasesimulatormodellib.domain.response.OperatorSupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperatorSupplierMapper {

	OperatorSupplierMapper INSTANCE = Mappers.getMapper(OperatorSupplierMapper.class);

	static OperatorSupplierMapper operatorSupplierMapper() {
		return INSTANCE;
	}

	OperatorSupplierResponse entityToResponse(OperatorSupplierEntity entity);

	OperatorSupplierEntity responseToEntity(OperatorSupplierResponse response);
}
