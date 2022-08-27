package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BaseValueProductPerBranchPurchaseRequest {
    
	@ApiModelProperty(value = "Código do Centro de Distribuição", example = "900")
    @Positive(message = "Centro de Distribuição não pode ser negativo")
    @NotNull
    private Long distributionCenterId;
    
    
    @ApiModelProperty(value = "Lista de Código do Produto", example = "[2,331]")
    @NotNull
    private List <Long> productIds;
}
