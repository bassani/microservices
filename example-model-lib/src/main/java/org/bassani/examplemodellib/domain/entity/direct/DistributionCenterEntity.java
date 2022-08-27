package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_CD_REGIONAL")
@Where(clause = "FL_REPLICAR_PEDIDO = true")
@Builder
public class DistributionCenterEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_REGIONAL", nullable = false)
	private Long id;

	@Column(name = "NM_CD_REGIONAL", nullable = false, length = 50)
	private String name;

	@Column(name = "CD_ESTEIRA", length = 5)
	private Integer idTreadmill;

	@Column(name = "DS_DB_LINK", length = 50)
	private String descriptionDbLink;

	@Column(name = "FL_ARRED_SUGESTAO", nullable = false, length = 1)
	private Integer flagArredSuggestion;

	@Column(name = "QT_CAPACIDADE_FAT_ITENS_DIA", length = 10)
	private Integer amountCapacityFatItemsDay;

	@Column(name = "QT_CAPACIDADE_RECEBIMENTO", nullable = false, length = 8)
	private Integer amountCapacityReceivement;

	@Column(name = "FL_REPLICAR_PEDIDO", length = 1)
	private Integer flagReplicateOrder;

	@Column(name = "FL_CONSULTAR_ESTOQUE_MS", length = 1)
	private Integer flagConsultStockMs;

	@Column(name = "FL_ECOMMERCE", nullable = false, length = 1)
	private Integer flagEcommerce;

}
