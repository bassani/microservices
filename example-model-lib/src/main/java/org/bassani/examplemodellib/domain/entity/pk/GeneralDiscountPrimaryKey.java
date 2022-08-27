package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GeneralDiscountPrimaryKey implements Serializable {

	private static final long serialVersionUID = 8433897902918619475L;

	private Long parameterSimulation;
}
