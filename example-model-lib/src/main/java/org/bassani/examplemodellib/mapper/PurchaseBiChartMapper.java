package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.PurchaseBiChartProjection;
import br.com.example.purchasesimulatormodellib.domain.response.PurchaseBiChartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PurchaseBiChartMapper {

    PurchaseBiChartMapper INSTANCE = Mappers.getMapper(PurchaseBiChartMapper.class);

    static PurchaseBiChartMapper purchaseBiChartMapper() {
        return INSTANCE;
    }

    PurchaseBiChartResponse toResponse(PurchaseBiChartProjection purchaseBiChartProjection);
}
