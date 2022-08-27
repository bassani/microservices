package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimulationSummaryFollowUpResponse {

    @ApiModelProperty(value = "Id", example = "10")
    private Long simulationId;

    @ApiModelProperty(value = "Valor da Compra")
    private Double totalAmountWithNegotiatedDiscount;

    @ApiModelProperty(value = "Quantidade Total")
    private Integer orderedQty;

}
