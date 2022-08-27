package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.CriticalProductPrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CriticalProductPrimaryKey.class)
@Table(name = "TB_PRODUTO_CRITICO")
public class CriticalProductEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_PRODUTO")
	private Long productId;

	@Id
	@Column(name = "CD_REGIONAL")
	private Long regionalId;

	@Column(name = "QT_DIAS_FREQUENCIA")
	private Long daysFrequencyQt;
}
