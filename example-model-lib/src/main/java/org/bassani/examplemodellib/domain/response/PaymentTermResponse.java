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
@Deprecated
public class PaymentTermResponse {

	@ApiModelProperty(value = "ID da Condição de Pagamento", example = "26")
	private Long id;

	@ApiModelProperty(value = "Descrição da Condição de Pagamento", example = "45/60/90 DD")
	private String name;
	
	@ApiModelProperty(value = "Quantidade de parcela", example = "3")
	private Integer qtdPracela;
	
	@ApiModelProperty(value = "Porcentagem de desconto", example = "26")
	private Double pcDesconto;
}
