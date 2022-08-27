package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class DistributionCenterSimParamPrimaryKey implements Serializable {

	private Long simulationParameters;
	
	private Long id;
}
