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
public class ProductDiscountSimParamRequest {

	@ApiModelProperty(value = "ID do parâmetro de simulação", example = "1")
	@NotNull
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Código produto", example = "10")
	@NotNull
	private Long produtoId;
	
	private Double registerDiscount;
	
	@ApiModelProperty(value = "Código do tipo de desconto", example = "11")
	private Integer discountType;
	
	@ApiModelProperty(value = "Valor do desconto parametrizado", example = "5.5")
	private Double parameterDiscount;
}