package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TB_PEDIDO_COMPRA")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1927919561249358116L;

    @Id
    @Column(name = "ID_PEDIDO_COMPRA")
    private Long orderId;

    @Column(name = "CD_FILIAL")
    private Long branchId;

    @Column(name = "CD_OPERACAO_FISCAL")
    private Long taxOperationId;

    @Column(name = "CD_TP_TRIB_CLIENTE")
    private Long customerTaxationType;

    @Column(name = "NR_PEDIDO_COMPRA")
    private Long orderNumber;

    @Column(name = "NR_RELAT_COMPRAS")
    private Long purchaseReportNumber;

    @Column(name = "CD_PEDIDO_COMPRA_STATUS")
    private Long orderStatusId;

    @Column(name = "CD_CONDICAO_PAGTO")
    private Long paymentTermId;

    @Column(name = "NR_ENDERECO_GERAL")
    private Long generalAddressNumber;

    @Column(name = "CD_FORNECEDOR")
    private Long supplierId;

    @Column(name = "DT_EMISSAO")
    private LocalDateTime sentDate;

    @Column(name = "DT_IMPRESSAO")
    private LocalDate printedDate;

    @Column(name = "DT_ENCERRAMENTO")
    private LocalDate closingDate;

    @Column(name = "VL_TOTAL_PEDIDO")
    private BigDecimal totalOrderValue;

    @Column(name = "VL_FRETE")
    private Long shippingCost;

    @Column(name = "DS_OBSERVACOES", length = 512)
    private String observations;

    @Column(name = "FL_TRANSMITIDO")
    private Boolean transmittedFlag;

    @Column(name = "FL_RETRANSMITIR")
    private Boolean retransmittedFlag;

    @Column(name = "DT_ATUALIZACAO_PDV")
    private LocalDateTime pdvUpdateDate;

    @Column(name = "QT_TE_DIGITADO")
    private Long typedTEQuantity;

    @Column(name = "DT_ENTREGA")
    private LocalDate deliveryDate;

    @Column(name = "DT_REENTREGA")
    private LocalDate redeliveryDate;

    @Column(name = "CD_PEDIDO_COMPRA_TIPO")
    private Long orderTypeId;

    @Column(name = "DT_FIM_ANTECIPACAO")
    private LocalDate anticipationFinalDate;

    @Column(name = "QT_DIAS_ESTOQUE_CD")
    private Long stockDaysDCQuantity;

    @Column(name = "QT_DIAS_ESTOQUE_APOS_ENTREGA")
    private Long stockDaysAfterDeliveredQuantity;

    @Column(name = "CD_OPERADOR_APROV")
    private Long approverOperatorId;

    @Column(name = "DT_ORCAMENTO_APROV")
    private LocalDate approvedBudgetDate;

    @Column(name = "CD_MOTIVO_REAGENDA_PED_COMPRA")
    private Long orderReScheduleReason;

    @Column(name = "VL_GANHO_NEGOCIACAO")
    private Long gainValue;

    @Column(name = "QT_TEMPO_ESPERA")
    private Long waitingTimeQuantity;

    @Column(name = "QT_TEMPO_FREQUENCIA")
    private Long frequencyTimeQuantity;

    @Column(name = "CD_OPERADOR")
    private Long operatorId;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "id.orderId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderItemEntity> items = new LinkedHashSet<>();

}
