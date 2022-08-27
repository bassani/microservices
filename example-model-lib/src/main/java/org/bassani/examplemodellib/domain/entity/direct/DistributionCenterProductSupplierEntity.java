package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.DistributionCenterProductSupplierPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_FORNECEDOR_PRODUTO_REGIONAL")
@IdClass(DistributionCenterProductSupplierPrimaryKey.class)
public class DistributionCenterProductSupplierEntity implements Serializable {

	private static final long serialVersionUID = 6790666924425025513L;

	@Id
	@Column(name = "CD_FORNECEDOR")
	private Long supplierId;

	@Id
	@Column(name = "CD_PRODUTO")
	private Long productId;

	@Id
	@Column(name = "CD_REGIONAL")
	private Long distributionCenterId;

	@Column(name = "CD_ARREDONDAMENTO")
	private Long roundingId;

	@Column(name = "CD_OPERADOR")
	private Long operatorId;

	@Column(name = "VL_PRECO_TABELA")
	private BigDecimal listPriceValue;

	@Column(name = "QT_CX_EMBARQUE")
	private Long boardingCxQt;

	@Column(name = "QT_CMD_PALLET")
	private Long palletCmdQt;

	@Column(name = "QT_PALLET")
	private Long palletQt;

	@Column(name = "QT_UNID_ABASTECIMENT_REGIONAL")
	private Long regionalSupplyUnitQt;

	@Column(name = "QT_CX_DISPLAY")
	private Long displayCxQt;

	@Column(name = "PC_ARREDONDAMENTO")
	private Long roundingPc;

	@Column(name = "PC_DESC_FINANCEIRO")
	private BigDecimal financialDescPc;

	@Column(name = "PC_DESC_COMERCIAL")
	private BigDecimal commercialDescPc;

	@Column(name = "PC_1CX_DISPLAY")
	private Long display1CxPc;

	@Column(name = "PC_CX_DISPLAY")
	private Long displayCxPc;

	@Column(name = "PC_1CX_EMBARQUE")
	private Long boarding1CxPc;

	@Column(name = "PC_CX_EMBARQUE")
	private Long boardingCxPc;

	@Column(name = "PC_CMD_PALLET")
	private Long palletCmdPc;

	@Column(name = "PC_PALLET")
	private Long palletPc;

	@Column(name = "DT_ULT_PEDIDO")
	private Date lastOrderDate;

	@Column(name = "DT_ATUALIZACAO")
	private Date updateDate;

	@Column(name = "DT_CADASTRO")
	private Date registerDate;

}
