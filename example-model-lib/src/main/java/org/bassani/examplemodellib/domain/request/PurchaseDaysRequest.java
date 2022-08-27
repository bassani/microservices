package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PurchaseDaysRequest {

    @ApiModelProperty(value = "Código do produto", example = "[11]")
    @NotNull
    private List<Long> productIds;

    @ApiModelProperty(value = "Código do Centro de distribuição", example = "900")
    @NotNull
    private Long distributionCenterId;

    @ApiModelProperty(value = "Código do fornecedor", example = "12")
    @NotNull
    private Long supplierId;
}
