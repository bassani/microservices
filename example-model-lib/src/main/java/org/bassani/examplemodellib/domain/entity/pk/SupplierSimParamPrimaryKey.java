package org.bassani.examplemodellib.domain.entity.pk;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationParametersEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class SupplierSimParamPrimaryKey implements Serializable {

    private static final long serialVersionUID = -3103983823442730611L;

    private SimulationParametersEntity simulationParameters;

    private Long id;
}