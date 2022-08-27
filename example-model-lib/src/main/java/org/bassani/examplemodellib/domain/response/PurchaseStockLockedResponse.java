package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PurchaseStockLockedResponse {

    @ApiModelProperty(value = "ID do produto", example = "1")
    Long productId;

    @ApiModelProperty(value = "Quantidade total de trava de estoque", example = "1")
    Long totalLocked;

}