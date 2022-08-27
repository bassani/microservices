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
@Table(name = "TB_SIMULACAO_PARAM_PEDIDO_COMPRA_TIPO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTypeParamEntity implements Serializable {

    private static final long serialVersionUID = -8312545351378637304L;

    @ToString.Exclude
    @MapsId
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_SIMULACAO_PARAM_PEDIDO_COMPRA_TIPO_01"))
    private SimulationParametersEntity simulationParameters;

    @Column(name = "CD_PEDIDO_COMPRA_TIPO")
    private Long id;

    @Column(name = "DS_PEDIDO_COMPRA_TIPO")
    private String name;

}
