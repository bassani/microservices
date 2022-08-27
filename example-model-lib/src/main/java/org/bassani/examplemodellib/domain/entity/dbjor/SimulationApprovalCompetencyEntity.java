package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.SimulationApprovalCompetencyPrimaryKey;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_SIMULACAO_APROVADOR_FINAL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@IdClass(SimulationApprovalCompetencyPrimaryKey.class)
public class SimulationApprovalCompetencyEntity implements Serializable {

    private static final long serialVersionUID = -3021723088179508989L;

    @Id
    @Column(name = "ID_SIMULACAO")
    private Long simulationId;

    @Id
    @Column(name = "CD_PERFIL")
    private Long profileId;

    @ToString.Exclude
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_APROVADOR_FINAL_01"))
    private SimulationParametersEntity simulationParameters;

    @ToString.Exclude
    @MapsId
    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_PERFIL", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_APROVADOR_FINAL_02"))
    private ProfileEntity profile;

}
