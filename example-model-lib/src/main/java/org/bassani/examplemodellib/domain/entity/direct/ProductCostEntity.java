package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.ProductCostPrimaryKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "TB_CUSTO_PRODUTO_RD")
@Entity
@IdClass(ProductCostPrimaryKey.class)
@Getter
@Setter
public class ProductCostEntity implements Serializable {

    @Id
    @Column(name = "CD_PRODUTO")
    Long productId;

    @Id
    @Column(name = "SG_ESTADO")
    String stateAcronym;

    @Column(name = "VL_CUSTO_MEDIO", nullable = false)
    BigDecimal averageCost;
}
