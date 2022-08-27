package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class QuantityPendingOrdersResponse {

	@ApiModelProperty(value = "Código do produto", example = "145", required = true)
	private Long productId;

	@ApiModelProperty(value = "Quantidade de Pedidos Pendentes", example = "92", required = true)
	private Long quantityPendingOrders;



}
