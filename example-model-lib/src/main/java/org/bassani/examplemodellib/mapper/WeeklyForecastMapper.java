package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.projection.WeeklyForecastProjection;
import br.com.example.purchasesimulatormodellib.domain.response.WeeklyForecastResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeeklyForecastMapper {

    WeeklyForecastMapper INSTANCE = Mappers.getMapper(WeeklyForecastMapper.class);

    static WeeklyForecastMapper weeklyForecastMapper() {
        return INSTANCE;
    }

    @Mapping(target = "targetDate", source = "targetDateId")
    @Mapping(target = "dayPeriod", source = "dayPeriodId")
    WeeklyForecastResponse projectionToResponse(WeeklyForecastProjection projection);
}
