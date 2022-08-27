package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuantityTypeSimulationResponse {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Código produto", example = "10")
	private Long produtoId;
	
	@ApiModelProperty(value = "Quantidade total solicitada", example = "1000")
	private Integer totalQuantity;
}
