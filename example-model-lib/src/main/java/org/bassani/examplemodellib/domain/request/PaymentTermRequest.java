package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTermRequest {

	@ApiModelProperty(value = "ID do Condição/Prazo de Pagamento", example = "0 | A Vista")
	private Long id;
	
	@ApiModelProperty(value = "Descrição da Condição/Prazo de Pagamento", example = "A Vista")
	private String name;

}