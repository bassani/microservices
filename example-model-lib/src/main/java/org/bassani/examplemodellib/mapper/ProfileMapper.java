package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ProfileEntity;
import br.com.example.purchasesimulatormodellib.domain.response.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {

	ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

	static ProfileMapper profileMapper() {
		return INSTANCE;
	}

	ProfileResponse entityToResponse(ProfileEntity entity);

	ProfileEntity responseToEntity(ProfileResponse response);
}
