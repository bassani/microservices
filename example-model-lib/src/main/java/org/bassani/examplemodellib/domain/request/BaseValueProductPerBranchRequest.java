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
public class BaseValueProductPerBranchRequest {
    @ApiModelProperty(value = "Código da categoria", example = "7381")
    @Positive(message = "Centro de Distribuição não pode ser negativo")
    @NotNull
    private Long distributionCenterId;
    @ApiModelProperty(value = "Descrição da categoria do produto", example = "SUPLEMENTOS")
    @NotNull
    private List <Long> productIds;
}
