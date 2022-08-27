package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuantityTypeSimulationRequest {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	@NotNull
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Código produto", example = "10")
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(value = "Quantidade total solicitada", example = "1000")
	private Integer totalQuantity;
}
