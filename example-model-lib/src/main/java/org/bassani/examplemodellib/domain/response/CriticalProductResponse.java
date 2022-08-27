package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CriticalProductResponse {

    @ApiModelProperty(value = "ID do produto", example = "1")
    Integer productId;

    @ApiModelProperty(value = "ID do centro de distribuição", example = "1446")
    Integer regionalDistributionCenter;

    @ApiModelProperty(value = "Quantidade dias de frequencia", example = "15")
    Integer frequencyDaysQuantity;

}
