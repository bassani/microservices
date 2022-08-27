package org.bassani.examplemodellib.domain.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface WeeklyForecastProjection {

    Long getDayPeriodId();

    Long getTargetDateId();

    Long getStepNumber();

    Long getDistributionCenterId();

    Long getProductId();

    Long getPredictionQt();

    BigDecimal getErrorVl();

    LocalDateTime getInclusionDate();

    LocalDateTime getUpdateDate();

    Integer getLoadedFlag();
}
