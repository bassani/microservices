package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockSumarizedResponse {

    @ApiModelProperty(value = "Produto", name = "productId", dataType = "Long", example = "8888")
    private Long productId;

    @ApiModelProperty(value = "Quantidade de estoque", name = "stockQuantity", dataType = "Long", example = "100")
    private Long total;

}