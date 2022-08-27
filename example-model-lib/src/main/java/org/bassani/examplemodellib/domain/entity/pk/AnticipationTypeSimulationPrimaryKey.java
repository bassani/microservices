package org.bassani.examplemodellib.domain.entity.pk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class AnticipationTypeSimulationPrimaryKey implements Serializable {
    private static final long serialVersionUID = -5399287664499552974L;
    private Long simulationParameters;
}
