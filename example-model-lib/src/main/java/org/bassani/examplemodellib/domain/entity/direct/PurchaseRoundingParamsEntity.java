package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.PurchaseRoundingParamsPrimaryKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "TB_PRODUTO_REGIONAL")
@Entity
@IdClass(PurchaseRoundingParamsPrimaryKey.class)
@Getter
@Setter
public class PurchaseRoundingParamsEntity implements Serializable {
	private static final long serialVersionUID = 5104400260673679308L;

	@Id
	@Column(name = "CD_REGIONAL")
	Integer distributionCenterId;
	
	@Id
    @Column(name = "CD_PRODUTO")
    Integer productId;
	
    @Column(name = "QT_MULTIPLO_FATURAMENTO")
    Integer qtyMultipleBilling;
    
    @Column(name = "PC_ARRED_SUGESTAO")
    BigDecimal percentageRoundingSuggestion;
    
}
