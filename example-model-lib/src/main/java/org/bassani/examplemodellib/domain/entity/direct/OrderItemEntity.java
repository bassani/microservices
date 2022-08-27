package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.OrderItemPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PEDIDO_COMPRA_ITEM")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity implements Serializable {
    private static final long serialVersionUID = -8142730600096642988L;

    @EmbeddedId
    private OrderItemPrimaryKey id;

    @Column(name = "CD_PRODUTO")
    private Long productId;

    @Column(name = "DT_PREVISAO_ENTREGA")
    private LocalDate deliveryForecastDate;

    @Column(name = "QT_PEDIDA")
    private Long orderedQuantity;

    @Column(name = "QT_ENTREGUE")
    private Long deliveredQuantity;

    @Column(name = "QT_SUSPENSA")
    private Long suspendedQuantity;

    @Column(name = "VL_UNITARIO_BRUTO")
    private Long totalUnitaryValue;

    @Column(name = "VL_UNITARIO_LIQUIDO")
    private Long liquidUnitaryValue;

    @Column(name = "VL_CUSTO_RAIA")
    private Long costValue;

    @Column(name = "PC_DESCONTO_FINANCEIRO")
    private Long financialDiscountPercent;

    @Column(name = "DT_ULT_ENTREGA")
    private LocalDate lastDeliveryDate;

    @Column(name = "NR_ULT_NF")
    private Long lastBillingNumber;

    @Column(name = "CD_UNIDADE_CONVERSAO")
    private Long conversionUnityId;

    @Column(name = "DT_ATUALIZACAO_PDV")
    private LocalDate pdvUpdateDate;

    @Column(name = "QT_SUGERIDA")
    private Long suggestedQuantity;

    @Column(name = "QT_SUGESTAO")
    private Long suggestionDate;

    @Column(name = "CD_EDI_MOTIVO_RESPOSTA")
    private Long ediReasonResponseId;

    @Column(name = "QT_RESPOSTA")
    private Long responseQuantity;

    @Column(name = "QT_DIAS_ESTOQUE_CD")
    private Long dailyStockDCQuantity;

    @Column(name = "QT_DIAS_ESTOQUE_APOS_ENTREGA")
    private Long dailyStockAfterDelivered;

    @Column(name = "CD_ARREDONDAMENTO")
    private Long roundingId;

    @Column(name = "PC_ARREDONDAMENTO")
    private Long roundedPercent;

    @Column(name = "QT_PEDIDO_COMPLEMENTO")
    private Long orderQuantityFulfill;

    @Column(name = "QT_DIFERENCA_FILIAL")
    private Long branchDiffQuantity;

    @Column(name = "QT_TF_ADICIONAL")
    private Long adictionalTFQuantity;

    @Column(name = "QT_ESTOQUE_CD")
    private Long stockDCQuantity;

    @Column(name = "QT_PEDIDO_PENDENTE")
    private Long pendingOrderQuantity;

    @Column(name = "QT_VENDA")
    private Long saleQuantity;

    @Column(name = "DT_EMISSAO")
    private LocalDateTime sentDate;

}
