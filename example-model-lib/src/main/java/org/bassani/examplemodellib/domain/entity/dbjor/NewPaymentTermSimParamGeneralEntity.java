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
@Table(name = "TB_SIMULACAO_PARAM_NOVO_PRAZO_GERAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPaymentTermSimParamGeneralEntity implements Serializable {

    private static final long serialVersionUID = -8312545351378637305L;

    @Id
    @MapsId
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "ID_SIMULACAO", referencedColumnName = "ID_SIMULACAO",
            foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAM_NOVO_PRAZO_GERAL_01"))
    private SimulationParametersEntity simulationParameters;

    @Column(name = "CD_CONDICAO_PAGTO", nullable = false)
    private Long newPaymentTermCode;

    @Column(name = "DS_CONDICAO_PAGTO", nullable = false)
    private String newPaymentTermDescription;

    @Column(name = "QT_DIAS_PAGTO", nullable = false)
    private Long daysQuantityPayment;

}
