package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class BudgetSimParamResponse {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Código da verba", example = "2")
	private Long code;

    @ApiModelProperty(value = "Valor da verba", example = "2")
    private BigDecimal value;

    @ApiModelProperty(value = "Descrição da verba", example = "2")
    private String description;
}
