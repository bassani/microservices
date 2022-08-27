package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BillingSupplierToPurchaseResponse {

	@ApiModelProperty(value = "Código do Fabricante", example = "6")
	private Long id;

	@ApiModelProperty(value = "Nome do Fabricante", example = "ACHE")
	private String name;
}