package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BillingSupplierParamRequest {

    @ApiModelProperty(value = "Código do Centro de Distribuição", example = "900")
    private Long distributionCenterId;

    @ApiModelProperty(value = "Código do Fabricante", example = "900")
    private Long manufacterId;
}
