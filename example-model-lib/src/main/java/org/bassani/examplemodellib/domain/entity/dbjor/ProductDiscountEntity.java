package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.ProductDiscountPrimaryKey;
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
import java.math.BigDecimal;

@Entity
@Table(name = "TB_DESCONTO_PRODUTO")
@IdClass(ProductDiscountPrimaryKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDiscountEntity implements Serializable {

    private static final long serialVersionUID = 5504742717455811265L;

    @Id
    @MapsId
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_DESCONTO_PRODUTO_01"))
	@ToString.Exclude
	private SimulationParametersEntity simulationParameters;
	
	@Id
	@Column(name = "CD_PRODUTO")
	private Long id;

	@ToString.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "CD_TIPO_DESCONTO", foreignKey = @ForeignKey(name = "FK_TB_DESCONTO_PRODUTO_02"))
	private DiscountTypeEntity type;
	
	@Column(name = "VL_DESCONTO_PARAM")
	private BigDecimal value;
}