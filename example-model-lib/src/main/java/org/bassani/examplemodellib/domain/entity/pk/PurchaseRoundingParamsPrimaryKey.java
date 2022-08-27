package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class PurchaseRoundingParamsPrimaryKey implements Serializable {
	private static final long serialVersionUID = -6634069245938895565L;
	
	Long productId;
	Long distributionCenterId;
}
