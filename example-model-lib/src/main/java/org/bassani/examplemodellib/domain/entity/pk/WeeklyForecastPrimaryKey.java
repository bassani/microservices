package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class WeeklyForecastPrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long dayPeriodId;
    private Long targetDateId;
    private Long stepNumber;
    private Long distributionCenterId;
    private Long productId;

}
