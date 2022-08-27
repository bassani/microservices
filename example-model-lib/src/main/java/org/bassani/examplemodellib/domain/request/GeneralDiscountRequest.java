package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralDiscountRequest implements Serializable {

	private static final long serialVersionUID = 3480849950525590007L;

	@ApiModelProperty(value = "Código do parâmetro de simulação", example = "1")
	@NotNull
	private Long parameterSimulationId;

	@ApiModelProperty(value = "Código do tipo de desconto", example = "11")
	private Integer discountTypeId;
	
	@ApiModelProperty(value = "Valor do desconto", example = "5.5")
	private BigDecimal value;
}