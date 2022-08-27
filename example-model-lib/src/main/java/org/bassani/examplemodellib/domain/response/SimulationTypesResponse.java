package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SimulationTypesResponse {
    @ApiModelProperty(value = "Código da simulação", example = "10")
    private Long id;
    @ApiModelProperty(value = "Nome da simulação", example = "PBM")
    private String name;
}
