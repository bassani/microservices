package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
public class BillingSupplierResponse implements Serializable {

    private static final long serialVersionUID = -9004244246216241242L;

    @ApiModelProperty(value = "Código do Fornecedor de Faturamento", example = "1256")
	public Long id;

	@ApiModelProperty(value = "Nome do Fornecedor de Faturamento", example = "ACHE")
	private String billingSupplierName;

    @ApiModelProperty(value = "Valor mínimo do pedido", example = "1500.00")
    public BigDecimal minOrderValue;
}