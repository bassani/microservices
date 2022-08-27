package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValueTypeSimulationRequest {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	@NotNull
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Valor da Compra (R$)", example = "1200.00")
	@NotNull
	private BigDecimal totalValue;
}
