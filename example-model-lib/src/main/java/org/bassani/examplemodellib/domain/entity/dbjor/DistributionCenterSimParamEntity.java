package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.DistributionCenterSimParamPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_SIMULACAO_PARAM_CD_REGIONAL")
@IdClass(DistributionCenterSimParamPrimaryKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionCenterSimParamEntity implements Serializable {

    private static final long serialVersionUID = -1450830889266160971L;

    @Id
    @MapsId
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAM_CD_REGIONAL_01"))
	@ToString.Exclude
	private SimulationParametersEntity simulationParameters;
	
	@Id
	@Column(name = "CD_REGIONAL")
	private Long id;

    @Column(name = "NM_CD_REGIONAL")
    private String name;

}