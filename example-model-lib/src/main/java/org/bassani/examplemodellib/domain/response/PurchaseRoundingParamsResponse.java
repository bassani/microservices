package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRoundingParamsResponse implements Serializable {
	
	private static final long serialVersionUID = 6862060062031641005L;

	@ApiModelProperty(value = "Código produto", example = "10")
	private Long productId;

	@ApiModelProperty(value = "Quantidade Multiplo Faturamento", example = "1")
	private Long multipleBillingQuantity;

	@ApiModelProperty(value = "Porcentagem Arredondamento Sugestão", example = "0.1")
	private BigDecimal roundingPercentageSuggestion;

}