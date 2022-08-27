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

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest implements Serializable {

    private static final long serialVersionUID = -2655458497235615301L;

    @ApiModelProperty(value = "Identificador do pedido de compra")
    private Long orderId;

    @ApiModelProperty(value = "Código da filial")
    private Long branchId;

    @ApiModelProperty(value = "Código da operação fiscal")
    private Long taxOperationId;

    @ApiModelProperty(value = "Tipo da tributação do cliente")
    private Long customerTaxationType;

    @ApiModelProperty(value = "Numero do pedido de compra")
    private Long orderNumber;

    @ApiModelProperty(value = "Numero do relatório de compra")
    private Long purchaseReportNumber;

    @ApiModelProperty(value = "Código do status do pedido de compra")
    private Long orderStatusId;

    @ApiModelProperty(value = "Código do prazo de pagamento")
    private Long paymentTermId;

    @ApiModelProperty(value = "Identificador do endereço geral")
    private Long generalAddressNumber;

    @ApiModelProperty(value = "Código do fornecedor")
    private Long supplierId;

    @ApiModelProperty(value = "Data da emissão")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentDate;

    @ApiModelProperty(value = "Data impressao")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate printedDate;

    @ApiModelProperty(value = "Data encerramento")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate closingDate;

    @ApiModelProperty(value = "Valor total do pedido de compra")
    private BigDecimal totalOrderValue;

    @ApiModelProperty(value = "Custo de frete")
    private Long shippingCost;

    @ApiModelProperty(value = "Observações")
    @Size(max = 512)
    private String observations;

    @ApiModelProperty(value = "Pedido transmitido")
    private Boolean transmittedFlag;

    @ApiModelProperty(value = "Pedido retransmitido")
    private Boolean retransmittedFlag;

    @ApiModelProperty(value = "Data de atualização PDV")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pdvUpdateDate;

    @ApiModelProperty(value = "TE digitado")
    private Long typedTEQuantity;

    @ApiModelProperty(value = "Data da entrega")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    @ApiModelProperty(value = "Data da re-entrega")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate redeliveryDate;

    @ApiModelProperty(value = "Tipo do pedido de compra")
    private Long orderTypeId;

    @ApiModelProperty(value = "Data final antecipação")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anticipationFinalDate;

    @ApiModelProperty(value = "Dias de estoque CD")
    private Long stockDaysDCQuantity;

    @ApiModelProperty(value = "Dias de estoque após entrega")
    private Long stockDaysAfterDeliveredQuantity;

    @ApiModelProperty(value = "Operador que aprovou")
    private Long approverOperatorId;

    @ApiModelProperty(value = "Orçamento aprovado")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate approvedBudgetDate;

    @ApiModelProperty(value = "Motivo re-agendamento")
    private Long orderReScheduleReason;

    @ApiModelProperty(value = "Ganho")
    private Long gainValue;

    @ApiModelProperty(value = "Quantidade em espera")
    private Long waitingTimeQuantity;

    @ApiModelProperty(value = "Quantidade lead time")
    private Long frequencyTimeQuantity;

    @ApiModelProperty(value = "Código do operador")
    private Long operatorId;

    @ApiModelProperty(value = "Itens do pedido de compra")
    private List<OrderItemRequest> items;
}
