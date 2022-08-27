package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_FORNECEDOR_AGENDA")
public class ScheduleSupplierEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_FORNECEDOR_AGENDA")
	private Long supplierScheduleId;

	@Column(name = "QT_DIAS_INTERVALO")
	private Long breakDaysQt;

	@Column(name = "DIA_SEMANA")
	private Long dayWeek;

	@Column(name = "FL_COMPRA")
	private Boolean isPurchase;

	@Column(name = "CD_FORNECEDOR")
	private Long supplierId;

	@Column(name = "CD_REGIONAL")
	private Long regionalId;

	@Column(name = "CD_FABRICANTE")
	private Long manufacturerId;

	@Column(name = "CD_PEDIDO_COMPRA_TIPO")
	private Long typePurchaseOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REGIONAL", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_TB_FORNECEDOR_AGENDA_01"))
    private DistributionCenterEntity distributionCenter;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
            @JoinColumn(name = "CD_FORNECEDOR", referencedColumnName = "CD_FORNECEDOR", insertable = false, updatable = false),
            @JoinColumn(name = "CD_REGIONAL", referencedColumnName = "CD_REGIONAL", insertable = false, updatable = false)
    })
    private DistributionCenterSupplierEntity distributionCenterSupplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FABRICANTE", referencedColumnName = "CD_FORNECEDOR", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_TB_FORNECEDOR_AGENDA_02"))
    private ManufacturerEntity manufacturer;


}
