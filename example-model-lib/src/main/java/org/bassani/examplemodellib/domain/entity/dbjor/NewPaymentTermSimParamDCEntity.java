package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.NewPaymentTermSimParamDCPrimaryKey;
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
@Table(name = "TB_SIMULACAO_PARAM_NOVO_PRAZO_CD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(NewPaymentTermSimParamDCPrimaryKey.class)
@Builder
public class NewPaymentTermSimParamDCEntity implements Serializable {

    private static final long serialVersionUID = 7779146181685206734L;

    @Id
    @MapsId
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAM_NOVO_PRAZO_CD_01"))
    @ToString.Exclude
    private SimulationParametersEntity simulationParameters;

    @Id
    @Column(name = "CD_REGIONAL", nullable = false)
    private Long distributionCenterId;

    @Column(name = "CD_CONDICAO_PAGTO", nullable = false)
    private Long newPaymentTermCode;

    @Column(name = "DS_CONDICAO_PAGTO", nullable = false)
    private String newPaymentTermDescription;

    @Column(name = "QT_DIAS_PAGTO", nullable = false)
    private Long daysQuantityPayment;

}
