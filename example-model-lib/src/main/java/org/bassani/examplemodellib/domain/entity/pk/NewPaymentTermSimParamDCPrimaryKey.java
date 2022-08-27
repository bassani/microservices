package org.bassani.examplemodellib.domain.entity.pk;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationParametersEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class NewPaymentTermSimParamDCPrimaryKey implements Serializable {

    private static final long serialVersionUID = 4108243935139199588L;

    private SimulationParametersEntity simulationParameters;
	
	private Long distributionCenterId;
}
