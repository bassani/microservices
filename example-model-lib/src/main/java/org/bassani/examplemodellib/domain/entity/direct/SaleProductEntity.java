package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.SaleProductPrimaryKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "TB_PRODUTO_VENDA_DIA_CD_REG")
@Entity
@IdClass(SaleProductPrimaryKey.class)
@Getter
@Setter
public class SaleProductEntity implements Serializable {

    @Id
    @Column(name = "CD_PRODUTO")
    Integer productId;

    @Id
    @Column(name = "DT_VENDA")
    LocalDate date;

    @Column(name = "QT_VENDA")
    Integer quantity;

    @Column(name = "VL_VENDA")
    BigDecimal value;

    @Column(name = "QT_VENDA_AGREGADA")
    Integer aggregatedQuantity;

    @Column(name = "VL_VENDA_AGREGADA")
    BigDecimal aggregatedValue;

    @Id
    @Column(name = "CD_REGIONAL")
    Integer regionalDistributionCenterId;

    @Column(name = "FL_COMPRA", nullable = false)
    Boolean isPurchase;

    @Column(name = "QT_VENDA_MULTICANAL")
    Integer multiChannelSaleQuantity;

    @Column(name = "VL_VENDA_MULTICANAL")
    BigDecimal multiChannelSaleValue;
}
