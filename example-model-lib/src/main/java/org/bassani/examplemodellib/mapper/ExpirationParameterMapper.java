package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.dto.ExpirationParameterRequest;
import br.com.example.purchasesimulatormodellib.domain.dto.ExpirationParameterResponse;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ExpirationParameterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpirationParameterMapper {

    ExpirationParameterMapper INSTANCE = Mappers.getMapper(ExpirationParameterMapper.class);

    static ExpirationParameterMapper expirationParameterMapper() {
        return INSTANCE;
    }

    @Mapping(source = "flowId", target = "expiration.id")
    ExpirationParameterEntity requestToEntity(ExpirationParameterRequest request);

    @Mapping(source = "expiration", target = "expirationFlow")
    ExpirationParameterResponse entityToResponse(ExpirationParameterEntity entity);

    @Mapping(source = "flowId", target = "expiration.id")
    List<ExpirationParameterEntity> listRequestToListEntity(List<ExpirationParameterRequest> request);

    @Mapping(source = "expiration", target = "expirationFlow")
    List<ExpirationParameterResponse> listEntityToListResponse(List<ExpirationParameterEntity> entity);

}