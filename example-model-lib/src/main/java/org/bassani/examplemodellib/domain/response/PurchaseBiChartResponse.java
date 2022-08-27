package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseBiChartResponse {

    @ApiModelProperty(value = "Mês", example = "2022-12-31")
    private LocalDate month;
    @ApiModelProperty(value = "CMV", example = "3123.12")
    private BigDecimal cmv;
    @ApiModelProperty(value = "Estoque (R$)", example = "3123.12")
    private BigDecimal stockValue;
    @ApiModelProperty(value = "Ciclo em dias", example = "11")
    private BigDecimal cycle;
    @ApiModelProperty(value = "Estoque (dias)", example = "11")
    private BigDecimal stockDays;

}
