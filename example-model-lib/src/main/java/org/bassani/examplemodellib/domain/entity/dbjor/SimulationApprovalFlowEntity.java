package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "TB_SIMULACAO_FLUXO_APROVACAO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SimulationApprovalFlowEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SequenceTbSimulationApprovalFlow")
    @SequenceGenerator(name = "SequenceTbSimulationApprovalFlow", sequenceName = "SQ_SIMULACAO_FLUXO_APROVACAO",
            allocationSize = 1, initialValue = 1)
    @Column(name = "ID_SIMULACAO_FLUXO_APROVACAO", length = 12)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_FLUXO_APROVACAO_01"))
    private SimulationParametersEntity simulationParameters;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_PERFIL", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_FLUXO_APROVACAO_02"))
    private ProfileEntity profile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_AREA", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_FLUXO_APROVACAO_03"))
    private AreaEntity area;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_SIMULACAO_FLUXO_APROVACAO_STATUS", foreignKey = @ForeignKey(name =
            "FK_TB_SIMULACAO_FLUXO_APROVACAO_04"))
    private SimulationApprovalStatusFlowEntity approvalFlowStatus;

    @Column(name = "CD_OPERADOR", length = 100)
    private Long operatorId;

    @Column(name = "ID_KEYCLOAK_USER")
    private String keycloakUserId;

    @Column(name = "DT_CRIACAO")
    private LocalDateTime dtCreated;

    @Column(name = "DS_MOTIVO")
    private String reason;
}
