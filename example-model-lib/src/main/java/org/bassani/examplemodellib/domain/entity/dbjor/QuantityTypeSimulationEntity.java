package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.QuantityTypeSimulationPrimaryKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SIMULACAO_TIPO_QUANTIDADE")
@IdClass(QuantityTypeSimulationPrimaryKey.class)
@Data
@NoArgsConstructor
public class QuantityTypeSimulationEntity {

	@Id
	@ToString.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_TIPO_QUANTIDADE_01"))
	private SimulationParametersEntity parameterSimulation;
	
	@Id
	@Column(name = "CD_PRODUTO")
	private Long produtoId;
	
	@Column(name = "QT_TOTAL_SOLICITADA")
	private Integer totalQuantity;
}