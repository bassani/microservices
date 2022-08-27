package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.PriceRangeValuePrimaryKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "TB_FAIXA_PRECO_VALOR")
@Entity
@IdClass(PriceRangeValuePrimaryKey.class)
@Getter
@Setter
public class PriceRangeValueEntity implements Serializable {

    @Id
    @Column(name = "CD_PRODUTO")
    Integer productId;

    @Id
    @Column(name = "CD_FAIXA_PRECO")
    Integer priceRangeId;

    @Column(name = "VL_CUSTO_RAIA_NOVO")
    BigDecimal newRaiaCost;
}
