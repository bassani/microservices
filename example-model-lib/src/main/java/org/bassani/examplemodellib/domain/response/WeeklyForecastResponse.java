package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyForecastResponse implements Serializable {

	private static final long serialVersionUID = 7386054713598901401L;

	private Long distributionCenterId;

	private Long productId;

	private Long dayPeriod;

	private Long targetDate;

	private Long stepNumber;

	private Long predictionQt;

	private BigDecimal errorVl;

}
