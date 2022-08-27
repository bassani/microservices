package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_SIMULACAO_PARAM_NOVO_PRAZO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPaymentTermSimParamEntity implements Serializable {

    private static final long serialVersionUID = 6739026436862795180L;

    @Id
    @Column(name = "ID_SIMULACAO")
    private Long simulationId;

    @MapsId
    @ToString.Exclude
    @OneToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAM_NOVO_PRAZO_01"))
    private SimulationParametersEntity simulationParameters;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_TIPO_NOVO_PRAZO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAM_NOVO_PRAZO_02"))
    private NewPaymentTermTypeEntity type;

    @ToString.Exclude
    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private NewPaymentTermSimParamGeneralEntity newPaymentTermGeneral;

    @ToString.Exclude
    @PrimaryKeyJoinColumn
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private List<NewPaymentTermSimParamDCEntity> newPaymentTermDCs;

    @PrePersist
    public void updateChildEntities() {
        if (getNewPaymentTermGeneral() != null) getNewPaymentTermGeneral().setSimulationParameters(this.simulationParameters);
        if (getNewPaymentTermDCs() != null) getNewPaymentTermDCs().forEach(val -> val.setSimulationParameters(this.simulationParameters));
    }

}
