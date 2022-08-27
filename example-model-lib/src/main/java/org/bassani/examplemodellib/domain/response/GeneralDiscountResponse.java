package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralDiscountResponse {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	private Long parameterSimulationId;
	
	@ApiModelProperty(value = "Código do tipo de desconto", example = "11")
	private Integer discountTypeId;
	
	@ApiModelProperty(value = "Valor do desconto parametrizado", example = "5.5")
	private BigDecimal value;
}