package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.CategorySimParamPrimaryKey;
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
@Table(name = "TB_SIMULACAO_PARAM_CATEGORIA")
@IdClass(CategorySimParamPrimaryKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySimParamEntity implements Serializable {

	private static final long serialVersionUID = -242905830055838530L;

    @Id
    @MapsId
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAM_CATEGORIA_01"))
    @ToString.Exclude
    private SimulationParametersEntity simulationParameters;

    @Id
	@Column(name = "CD_CATEGORIA")
	private Long id;

    @Column(name = "DS_CATEGORIA")
    private String name;
}
