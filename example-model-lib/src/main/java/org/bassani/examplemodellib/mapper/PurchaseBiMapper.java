package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.PurchaseBiProjection;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseBiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PurchaseBiMapper {

    PurchaseBiMapper INSTANCE = Mappers.getMapper(PurchaseBiMapper.class);

    static PurchaseBiMapper purchaseBiMapper() {
        return INSTANCE;
    }

    @Mapping(target = "periodDayDate", source = "periodDayDate")
    PurchaseBiResponse toResponse(PurchaseBiProjection purchaseBiProjection);
}
