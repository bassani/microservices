package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SaleProductResponse {

    @ApiModelProperty(value = "ID do produto", example = "1")
    Integer productId;

    @ApiModelProperty(value = "Data da venda", example = "")
    LocalDate date;

    @ApiModelProperty(value = "Quantidade da venda", example = "3")
    Integer quantity;

    @ApiModelProperty(value = "Valor da venda", example = "120.13")
    BigDecimal value;

    @ApiModelProperty(value = "Quantidade agregada da venda", example = "1")
    Integer aggregatedQuantity;

    @ApiModelProperty(value = "Valor agregado da venda", example = "25.77")
    BigDecimal aggregatedValue;

    @ApiModelProperty(value = "ID do CD regional", example = "900")
    Integer regionalDistributionCenterId;

    @ApiModelProperty(value = "Flag compra", example = "true")
    Boolean isPurchase;

    @ApiModelProperty(value = "Quantidade da venda multi canal", example = "1")
    Integer multiChannelSaleQuantity;

    @ApiModelProperty(value = "Valor da venda multi canal", example = "5.26")
    BigDecimal multiChannelSaleValue;
}
