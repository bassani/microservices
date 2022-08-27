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
public class BudgetSimParamRequest {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	@NotNull
	private Long simulationId;
	
	@ApiModelProperty(value = "Código da verba", example = "2")
	@NotNull
	private Long code;
}
