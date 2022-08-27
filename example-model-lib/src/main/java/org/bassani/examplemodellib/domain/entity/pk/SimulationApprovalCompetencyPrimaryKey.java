package org.bassani.examplemodellib.domain.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimulationApprovalCompetencyPrimaryKey implements Serializable {

    private static final long serialVersionUID = -9130699895764980203L;

    private Long simulationId;

    private Long profileId;
}
