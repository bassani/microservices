package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.ProductSupplierPrimaryKey;
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
@IdClass(ProductSupplierPrimaryKey.class)
@Table(name = "TB_FORNECEDOR_PRODUTO")
public class ProductSupplierEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_FORNECEDOR")
	private Long supplierId;

	@Id
	@Column(name = "CD_PRODUTO")
	private Long productId;

	@Column(name = "FL_PREFERENCIAL")
	private Boolean isPreferential;

	@Column(name = "FL_COMPRA")
	private Boolean isPurchase;

	@Column(name = "FL_FABRICANTE")
	private Boolean isManufacturer;

	@Column(name = "QT_UNIDADES_PADRAO_ABASTECIMEN")
	private Long patternUnitQt;

	@Column(name = "QT_DIAS_ENTREGA")
	private Long deliveryDaysQt;

	@Column(name = "QT_DIAS_MEDIA_ATRASO")
	private Long delayAverageDaysQt;

	@Column(name = "CD_UNIDADE_CONVERSAO")
	private Long conversionUnitId;

	@Column(name = "CD_PRODUTO_FORNECEDOR")
	private String supplierProductId;

	@Column(name = "DT_ATUALIZACAO")
	private Date updateDate;

	@Column(name = "CD_EAN")
	private Long eanId;

	@Column(name = "VL_PRECO_TABELA")
	private BigDecimal listPriceValue;

	@Column(name = "PC_DESC_FINANCEIRO")
	private BigDecimal financialDescPc;

	@Column(name = "PC_DESC_COMERCIAL")
	private BigDecimal commercialDescPc;

	@Column(name = "CD_UNID_CONV_ENTRADA")
	private Long inputConversionUnitId;

	@Column(name = "FL_FABRICANTE_FISCAL")
	private Boolean isFiscalManufacturer;

	@Column(name = "DT_ALT_FABRICANTE")
	private Date updateManufacturerDate;
}
