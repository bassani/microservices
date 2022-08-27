package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTermInstallmentResponse {

	@ApiModelProperty(value = "ID da Condição de Pagamento", example = "26")
	private Long id;

	@ApiModelProperty(value = "Descrição da Condição de Pagamento", example = "45/60/90 DD")
	private String conditionPaymentDescription;
	
	@ApiModelProperty(value = "Quantidade de dias", example = "3")
	private Long daysQuantityPayment;

}
