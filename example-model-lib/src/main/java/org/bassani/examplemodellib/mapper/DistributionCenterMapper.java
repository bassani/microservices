package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.DistributionCenterEntity;
import br.com.example.purchasesimulatormodellib.domain.response.DistributionCenterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DistributionCenterMapper {

	DistributionCenterMapper INSTANCE = Mappers.getMapper(DistributionCenterMapper.class);

	static DistributionCenterMapper distributionCenterMapper() {
		return INSTANCE;
	}

	DistributionCenterResponse entityToResponse(DistributionCenterEntity entity);

	DistributionCenterEntity responseToEntity(DistributionCenterResponse response);
}
