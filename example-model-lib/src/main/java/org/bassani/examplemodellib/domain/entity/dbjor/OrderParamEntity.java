package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PEDIDO_PARAMETRO")
public class OrderParamEntity implements Serializable{

    private static final long serialVersionUID = 3123379385547606434L;

    @EmbeddedId
    private OrderParamPrimaryKey id;

    @Column(name = "CD_FORNECEDOR")
    private Long supplierId;

    @Column(name = "CD_OPERACAO_FISCAL")
    private Long taxOperationId;

    @Column(name = "ID_PEDIDO_COMPRA")
    private Long orderId;

    @Column(name = "NR_PEDIDO_COMPRA")
    private Long orderNumber;

    @Column(name = "NR_RELAT_COMPRAS")
    private Long orderReportId;

    @Column(name = "FL_EMITIDO")
    private Boolean flagDispatch;

    @Column(name = "DS_MOTIVO")
    private String reason;

    @Column(name = "NR_ENDERECO_GERAL")
    private Long generalAddressNumber;

    @Column(name = "VL_TOTAL_PEDIDO")
    private BigDecimal totalValue;

    @Column(name = "CD_PEDIDO_COMPRA_TIPO")
    private Long purchaseOrderTypeId;

    @Column(name = "CD_OPERADOR")
    private Long operatorId;


    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderParamPrimaryKey implements Serializable {

        private static final long serialVersionUID = 5714659549065229940L;

        @Column(name = "ID_SIMULACAO")
        private Long simulationId;

        @Column(name = "CD_REGIONAL")
        private Long distributionCenterId;

        @Column(name = "CD_FORNECEDOR_FATURAMENTO")
        private Long billingSupplierId;

    }

}
