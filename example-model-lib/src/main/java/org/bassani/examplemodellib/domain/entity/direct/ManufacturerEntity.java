package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_FORNECEDOR")
public class ManufacturerEntity {

	@Id
	@Column(name = "CD_FORNECEDOR")
	public Long id;

	@Column(name = "NM_FANTASIA")
	public String name;

	@Column(name = "NM_RAZAO_SOCIAL")
	public String description;

	@Column(name = "NR_CNPJ")
	private String cnpjNumber;

	@Column(name = "NR_INSCR_ESTADUAL")
	private String stateRegistrationNr;

	@Column(name = "QT_DIAS_FREQUENCIA_PEDIDO")
	private Long orderFrequencyDaysQt;

	@Column(name = "QT_DIAS_MARGEM_SEGURANCA")
	private Long securityMarginDaysQt;

	@Column(name = "QT_QUEBRA_PEDIDO")
	private Long breakOrderQt;

	@Column(name = "PC_DESCONTO")
	private BigDecimal discountPc;

	@Column(name = "FL_DELETADO")
	private Boolean isDeleted;

	@Column(name = "FL_EDI")
	private Boolean isEdi;

	@Column(name = "FL_SITUACAO_CADASTRAL_RECEITA")
	private Boolean isRevenueRegistrationStatus;

	@Column(name = "FL_ENTREGA_SALDO")
	private Boolean isBalanceDelivery;

	@Column(name = "CD_EAN_FORNECEDOR")
	private String supplierEanId;

	@Column(name = "CD_TP_FORNECEDOR")
	private Long supplierTypeId;

	@Column(name = "CD_CONDICAO_PAGTO")
	private Long paymentTermsId;

	@Column(name = "CD_LAYOUT")
	private Long layoutId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FORNECEDOR_PAI")
    private ManufacturerEntity parent;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplierId")
    private List<ScheduleSupplierEntity> scheduleSuppliers;

}
