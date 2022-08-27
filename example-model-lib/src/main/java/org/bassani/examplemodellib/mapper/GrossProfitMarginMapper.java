package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.GrossProfitMarginEntity;
import br.com.example.purchasesimulatormodellib.domain.projection.GrossProfitMarginProjection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrossProfitMarginMapper {

    GrossProfitMarginMapper INSTANCE = Mappers.getMapper(GrossProfitMarginMapper.class);

    static GrossProfitMarginMapper grossProfitMarginMapper() { return INSTANCE; }

    GrossProfitMarginEntity toEntity(GrossProfitMarginProjection grossProfitMarginProjection);
}
