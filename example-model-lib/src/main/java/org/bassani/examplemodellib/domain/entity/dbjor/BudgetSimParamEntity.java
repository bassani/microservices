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
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_SIMULACAO_PARAMETRO_VERBA")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BudgetSimParamEntity implements Serializable {

    private static final long serialVersionUID = -647030114155377556L;

    @Id
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAMETRO_VERBA_01"))
    @ToString.Exclude
    private SimulationParametersEntity simulationParameters;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_TIPO_VERBA", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAMETRO_VERBA_02"))
    private BudgetTypeEntity type;

    @Column(name = "VL_VERBA")
    private BigDecimal value;

    @Column(name = "DS_MOTIVO")
    private String reason;
}