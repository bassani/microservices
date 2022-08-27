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
@Table(name = "TB_DESCONTO_GERAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralDiscountEntity implements Serializable {

    private static final long serialVersionUID = -6889688213180244388L;

    @ToString.Exclude
    @Id
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_DESCONTO_GERAL_01"))
    private SimulationParametersEntity simulationParameters;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_TIPO_DESCONTO", foreignKey = @ForeignKey(name = "FK_TB_DESCONTO_GERAL_02"))
    private DiscountTypeEntity type;

    @Column(name = "VL_DESCONTO")
    private BigDecimal value;
}