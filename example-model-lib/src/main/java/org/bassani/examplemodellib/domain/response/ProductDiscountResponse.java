package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDiscountResponse {

	@ApiModelProperty(value = "ID do parametro de simulação", example = "1")
	private Long parameterSimulation;
	
	@ApiModelProperty(value = "Código produto", example = "10")
	private Long produtoId;
	
	@ApiModelProperty(value = "Valor do desconto cadastrado", example = "10.34")
	private Double registerDiscount;
	
	@ApiModelProperty(value = "Código do tipo de desconto", example = "11")
	private Integer discountType;
	
	@ApiModelProperty(value = "Valor do desconto parametrizado", example = "5.5")
	private Double parameterDiscount;
}