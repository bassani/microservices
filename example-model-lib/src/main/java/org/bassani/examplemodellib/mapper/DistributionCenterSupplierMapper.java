package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.DistributionCenterSupplierEntity;
import br.com.example.purchasesimulatormodellib.domain.response.DistributionCenterSupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DistributionCenterSupplierMapper {

	DistributionCenterSupplierMapper INSTANCE = Mappers.getMapper(DistributionCenterSupplierMapper.class);

	static DistributionCenterSupplierMapper distributionCenterSupplierMapper() {
		return INSTANCE;
	}

	DistributionCenterSupplierResponse entityToResponse(DistributionCenterSupplierEntity entity);

	DistributionCenterSupplierEntity responseToEntity(DistributionCenterSupplierResponse response);
}
