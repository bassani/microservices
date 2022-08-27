package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.dto.ExpirationDTO;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ExpirationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpirationMapper {

    ExpirationMapper INSTANCE = Mappers.getMapper(ExpirationMapper.class);

    static ExpirationMapper expirationMapper() {
        return INSTANCE;
    }

    ExpirationDTO entityToDto(ExpirationEntity entity);

    List<ExpirationDTO> listEntityToListDto(List<ExpirationEntity> entity);

    ExpirationEntity dtoToEntity(ExpirationDTO expirationDTO);

    List<ExpirationEntity> listDtoToListEntity(List<ExpirationDTO> expirationDTO);

}