package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_SIMULACAO_TIPO_VALOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValueTypeSimulationEntity implements Serializable {

    private static final long serialVersionUID = -1040983449882212264L;

    @Column(name = "ID_SIMULACAO")
    @Id
    private Long simulationId;

    @ToString.Exclude
    @OneToOne
    @MapsId
	@JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_TIPO_VALOR_01"))
	private SimulationParametersEntity simulationParameters;
	
	@Column(name = "VL_TOTAL_PEDIDO")
	private BigDecimal totalValue;
}