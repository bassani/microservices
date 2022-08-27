package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationApprovalCompetencyResponse implements Serializable {

    private static final long serialVersionUID = -1576525607221358894L;

    @ApiModelProperty(value = "ID da Simulação", example = "1")
    private Long simulationId;

    @ApiModelProperty(value = "ID do Perfil (Cargo)", example = "1")
    private Long profileId;
}
