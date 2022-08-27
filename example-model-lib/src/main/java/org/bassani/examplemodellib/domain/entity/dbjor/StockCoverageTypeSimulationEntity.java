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

@Entity
@Table(name = "TB_SIMULACAO_TIPO_COBERT_ESTOQ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockCoverageTypeSimulationEntity implements Serializable {

    private static final long serialVersionUID = 441548017990480552L;

    @Id
    @Column(name = "ID_SIMULACAO")
    private Long simulationId;

    @ToString.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_TIPO_COBERT_ESTOQ_01"))
    private SimulationParametersEntity simulationParameters;
	
	@Column(name = "QT_DIA_COBERT_ESTOQ")
	private Long stockCoverageDays;
}