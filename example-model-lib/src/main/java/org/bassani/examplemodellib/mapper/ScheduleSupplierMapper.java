package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.direct.ScheduleSupplierEntity;
import br.com.example.purchasesimulatormodellib.domain.response.ScheduleSupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleSupplierMapper {

	ScheduleSupplierMapper INSTANCE = Mappers.getMapper(ScheduleSupplierMapper.class);

	static ScheduleSupplierMapper scheduleSupplierMapper() {
		return INSTANCE;
	}

	ScheduleSupplierResponse entityToResponse(ScheduleSupplierEntity entity);

	ScheduleSupplierEntity responseToEntity(ScheduleSupplierResponse response);
}
