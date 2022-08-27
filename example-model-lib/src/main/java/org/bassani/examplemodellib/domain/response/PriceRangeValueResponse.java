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
public class PriceRangeValueResponse {

    @ApiModelProperty(value = "ID do produto", example = "1")
    Integer productId;

    @ApiModelProperty(value = "ID da faixa de preço", example = "1")
    Integer priceRangeId;

    @ApiModelProperty(value = "Valor do novo custo Raia", example = "6.95")
    BigDecimal newRaiaCost;
}
