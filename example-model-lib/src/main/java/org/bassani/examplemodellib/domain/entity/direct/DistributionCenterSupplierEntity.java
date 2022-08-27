package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.DistributionCenterSupplierPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@IdClass(DistributionCenterSupplierPrimaryKey.class)
@Table(name = "TB_FORNECEDOR_REGIONAL")
public class DistributionCenterSupplierEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_FORNECEDOR")
	private Long supplierId;
	
	@Id
	@Column(name = "CD_REGIONAL")
	private Long regionalId;

	@Column(name = "QT_DIAS_ENTREGA")
	private Long deliveryDaysQt;

	@Column(name = "QT_DIAS_FREQUENCIA_SEG")
	private Long frequencyMonDaysQt;

	@Column(name = "QT_DIAS_FREQUENCIA_TER")
	private Long frequencyTueDaysQt;

	@Column(name = "QT_DIAS_FREQUENCIA_QUA")
	private Long frequencyWedDaysQt;

	@Column(name = "QT_DIAS_FREQUENCIA_QUI")
	private Long frequencyThuDaysQt;

	@Column(name = "QT_DIAS_FREQUENCIA_SEX")
	private Long frequencyFriDaysQt;

	@Column(name = "VL_MIN_PEDIDO")
	private BigDecimal orderMinVl;
}
