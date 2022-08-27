package org.bassani.examplemodellib.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderItemRequest {

    @ApiModelProperty(value = "Identificador do pedido de compra")
    private Long orderId;

    @ApiModelProperty(value = "Número de sequencia do item")
    private Long sequenceNumber;

    @ApiModelProperty(value = "Código do produto")
    private Long productId;

    @ApiModelProperty(value = "Data entrega prevista")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deliveryForecastDate;

    @ApiModelProperty(value = "Quantidade pedida")
    private Long orderedQuantity;

    @ApiModelProperty(value = "Quantidade entregue")
    private Long deliveredQuantity;

    @ApiModelProperty(value = "Quantidade suspensa")
    private Long suspendedQuantity;

    @ApiModelProperty(value = "Valor unitário bruto")
    private Long totalUnitaryValue;

    @ApiModelProperty(value = "Valor unitário líquido")
    private Long liquidUnitaryValue;

    @ApiModelProperty(value = "Custo")
    private Long costValue;

    @ApiModelProperty(value = "Percentual de desconto financeiro")
    private Long financialDiscountPercent;

    @ApiModelProperty(value = "Data última entrega")
    private LocalDate lastDeliveryDate;

    @ApiModelProperty(value = "Número última nota fiscal")
    private Long lastBillingNumber;

    @ApiModelProperty(value = "Código unidade conversão")
    private Long conversionUnityId;

    @ApiModelProperty(value = "Data atualização PDV")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate pdvUpdateDate;

    @ApiModelProperty(value = "Quantidade sugerida")
    private Long suggestedQuantity;

    @ApiModelProperty(value = "Quantidade sugestão")
    private Long suggestionDate;

    @ApiModelProperty(value = "Código motivo resposta EDI")
    private Long ediReasonResponseId;

    @ApiModelProperty(value = "Quantidade resposta")
    private Long responseQuantity;

    @ApiModelProperty(value = "Quantidade estoque no CD")
    private Long dailyStockDCQuantity;

    @ApiModelProperty(value = "Quantidade de dias estoque após entrega")
    private Long dailyStockAfterDelivered;

    @ApiModelProperty(value = "Código arredondamento")
    private Long roundingId;

    @ApiModelProperty(value = "Percentual arredondamento")
    private Long roundedPercent;

    @ApiModelProperty(value = "Quantidade complemento pedido")
    private Long orderQuantityFulfill;

    @ApiModelProperty(value = "Quantidade diferença filial")
    private Long branchDiffQuantity;

    @ApiModelProperty(value = "Quantidade TF adicional")
    private Long adictionalTFQuantity;

    @ApiModelProperty(value = "Quantidade estoque CD")
    private Long stockDCQuantity;

    @ApiModelProperty(value = "Quantidade pedido pendente")
    private Long pendingOrderQuantity;

    @ApiModelProperty(value = "Quantidade venda")
    private Long saleQuantity;

    @ApiModelProperty(value = "Data emissão")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentDate;
}
