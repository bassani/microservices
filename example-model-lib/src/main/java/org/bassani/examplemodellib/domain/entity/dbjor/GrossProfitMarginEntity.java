package org.bassani.examplemodellib.domain.entity.dbjor;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.GrossProfitMarginPrimaryKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@Table(name = "TB_MARGEM_LUCRO_BRUTO")
@IdClass(GrossProfitMarginPrimaryKey.class)
public class GrossProfitMarginEntity implements Persistable<GrossProfitMarginPrimaryKey> {

    @Override
    public GrossProfitMarginPrimaryKey getId() {
        GrossProfitMarginPrimaryKey a = new GrossProfitMarginPrimaryKey();
        a.setPeriodMonthCode(getPeriodMonthCode());
        a.setSupplierId(getSupplierId());
        a.setProductId(getProductId());
        return a;
    }

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PrePersist
    void trackNotNew() {
        this.isNew = false;
    }

    @Id
    @Column(name = "CD_PERIODO_MES")
    private Long periodMonthCode;

    @Id
    @Column(name = "CD_FORNECEDOR_SO")
    private Long supplierId;

    @Id
    @Column(name = "CD_PRODUTO")
    private Long productId;

    @Column(name = "NM_PRODUTO")
    private String productName;

    @Column(name = "CD_GRUPO_PRODUTO")
    private Long productGroupId;

    @Column(name = "NM_FANTASIA_FORNECEDOR")
    private String supplierName;

    @Column(name = "VL_RBV")
    private Double RbvValue;

    @Column(name = "VL_LBC")
    private Double LbcValue;
}
