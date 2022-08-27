package org.bassani.examplemodellib.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StockRequest {

    @NotNull
    @ApiModelProperty(value = "Centro de Distribuição ou Filiais", name = "distributionCenter", dataType = "Long", example = "[29, 1047]")
    private Set<@Positive Long> branchIds;

    @NotNull
    @ApiModelProperty(value = "Ids de produtos", name = "productIds", dataType = "Long", example = "[123, 456]")
    private Set<@Positive Long> productIds;

    @ApiModelProperty(value = "Tipos de estoque", name = "typeIds", dataType = "Long", example = "[4]")
    private Set<Long> typeIds;

}