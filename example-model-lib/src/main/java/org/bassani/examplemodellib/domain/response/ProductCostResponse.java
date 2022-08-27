package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductCostResponse {

    @ApiModelProperty(value = "ID do produto", example = "1")
    Long productId;

    @ApiModelProperty(value = "Sigla do estado", example = "TO")
    String stateAcronym;

    @ApiModelProperty(value = "Custo médio", example = "3.9125")
    BigDecimal averageCost;
}
