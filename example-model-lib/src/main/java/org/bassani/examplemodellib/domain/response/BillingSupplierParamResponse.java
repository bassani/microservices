package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BillingSupplierParamResponse {

    @ApiModelProperty(value = "Fornecedor de Faturamento")
    private BillingSupplierResponse billingSupplierResponse;

    @ApiModelProperty(value = "Centro de Distribuição")
    private DistributionCenterResponse distributionCenterResponse;

    @ApiModelProperty(value = "Fabricante")
    private ManufactureParamResponse manufactureParamResponse;

}
