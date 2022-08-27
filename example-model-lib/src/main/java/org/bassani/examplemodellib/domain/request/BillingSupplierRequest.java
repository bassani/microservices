package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingSupplierRequest {
    @ApiModelProperty(value = "Código do Fornecedor de Faturamento", example = "1256")
    public Long id;

    @ApiModelProperty(value = "Nome do Fornecedor de Faturamento", example = "ACHE")
    private String billingSupplierName;

    @ApiModelProperty(value = "Valor mínimo do pedido", example = "1500.00")
    public BigDecimal minOrderValue;

    private Long generalAddressNumber;

    public BillingSupplierRequest(Long id, String billingSupplierName, BigDecimal minOrderValue) {
        this.id = id;
        this.billingSupplierName = billingSupplierName;
        this.minOrderValue = minOrderValue;
    }
}
