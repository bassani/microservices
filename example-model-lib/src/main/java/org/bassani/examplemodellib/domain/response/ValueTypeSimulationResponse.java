package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValueTypeSimulationResponse {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Valor da Compra (R$)", example = "1200.00")
	private Double totalValue;
}
