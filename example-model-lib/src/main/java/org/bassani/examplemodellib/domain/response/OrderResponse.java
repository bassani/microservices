package org.bassani.examplemodellib.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderResponse {

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
    private LocalDateTime sentDate;

    @ApiModelProperty(value = "Data impressao")
    private LocalDate printedDate;

    @ApiModelProperty(value = "Data encerramento")
    private LocalDate closingDate;

    @ApiModelProperty(value = "Valor total do pedido de compra")
    private Long totalOrderValue;

    @ApiModelProperty(value = "Custo de frete")
    private Long shippingCost;

    @ApiModelProperty(value = "Observações")
    private String observations;

    @ApiModelProperty(value = "Pedido transmitido")
    private Boolean transmittedFlag;

    @ApiModelProperty(value = "Pedido retransmitido")
    private Boolean retransmittedFlag;

    @ApiModelProperty(value = "Data de atualização PDV")
    private LocalDateTime pdvUpdateDate;

    @ApiModelProperty(value = "TE digitado")
    private Long typedTEQuantity;

    @ApiModelProperty(value = "Data da entrega")
    private LocalDate deliveryDate;

    @ApiModelProperty(value = "Data da re-entrega")
    private LocalDate redeliveryDate;

    @ApiModelProperty(value = "Tipo do pedido de compra")
    private Long orderTypeId;

    @ApiModelProperty(value = "Data final antecipação")
    private LocalDate anticipationFinalDate;

    @ApiModelProperty(value = "Dias de estoque CD")
    private Long stockDaysDCQuantity;

    @ApiModelProperty(value = "Dias de estoque após entrega")
    private Long stockDaysAfterDeliveredQuantity;

    @ApiModelProperty(value = "Operador que aprovou")
    private Long approverOperatorId;

    @ApiModelProperty(value = "Orçamento aprovado")
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
    private List<OrderItemResponse> items;
}
