package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.DistributionCenterProductSupplierEntity;
import br.com.example.purchasesimulatormodellib.domain.response.DistributionCenterProductSupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DistributionCenterProductSupplierMapper {

	DistributionCenterProductSupplierMapper INSTANCE = Mappers.getMapper(DistributionCenterProductSupplierMapper.class);

	static DistributionCenterProductSupplierMapper dCProductSupplierMapper() {
		return INSTANCE;
	}

	DistributionCenterProductSupplierResponse entityToResponse(DistributionCenterProductSupplierEntity entity);

	DistributionCenterProductSupplierEntity responseToEntity(DistributionCenterProductSupplierResponse response);
}
