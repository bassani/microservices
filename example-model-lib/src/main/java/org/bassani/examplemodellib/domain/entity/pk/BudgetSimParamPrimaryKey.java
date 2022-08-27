package org.bassani.examplemodellib.domain.entity.pk;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationParametersEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class BudgetSimParamPrimaryKey implements Serializable {

    private static final long serialVersionUID = -6190748296004736866L;

    private SimulationParametersEntity simulationParameters;

}
