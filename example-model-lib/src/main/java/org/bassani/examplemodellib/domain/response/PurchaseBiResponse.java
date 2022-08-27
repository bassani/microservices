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
public class PurchaseBiResponse {

    @ApiModelProperty(value = "Periodo", example = "2")
    private LocalDate periodDayDate;
    @ApiModelProperty(value = "Código do Produto", example = "2")
    private Long productCode;
    @ApiModelProperty(value = "Filial", example = "2")
    private Long branchType;
    @ApiModelProperty(value = "CMV", example = "123.12")
    private BigDecimal cmv;
    @ApiModelProperty(value = "Estoque (R$)", example = "123.12")
    private BigDecimal stockValue;

}
