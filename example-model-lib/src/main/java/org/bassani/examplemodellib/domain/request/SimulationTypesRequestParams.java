package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationTypesRequestParams {
    @ApiModelProperty(value = "Campo de busca em todos os campos", example = "1 | Negociação")
    private String query;
    @ApiModelProperty(value = "Código da simulação", example = "10")
    private Long id;
    @ApiModelProperty(value = "Nome da simulação", example = "PBM")
    private String name;
}
