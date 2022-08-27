package org.bassani.examplemodellib.domain.entity.direct;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "TB_PARAMETRO_CURVA")
@Entity
public class CurveParameterEntity {
	
	@EmbeddedId
	private PrimaryKey key;

	@Column(name = "PC_ARRED_CAIXA_FECHADA")
	private Long closedBox;
	
	@Column(name = "PC_FATOR_SEGURANCA")
	private Long safetyFactor;
	
	@Data
	@Embeddable
	public static class PrimaryKey implements Serializable {

		@Column(name = "CL_PRODUTO_CURVA")
		private String curvedProduct;
		
		@ToString.Exclude
		@ManyToOne(optional = false)
		@JoinColumn(name = "CD_REGIONAL", foreignKey = @ForeignKey(name = "FK_TB_PARAMETRO_CURVA_01"))
		private DistributionCenterEntity distributionCenter;
		
		@Column(name = "CD_SUBGRUPO")
		private Long subGroup;
	}
}
