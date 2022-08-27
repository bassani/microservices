package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.BranchPurchasePrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "TB_FILIAL_COMPRA")
@Entity
@IdClass(BranchPurchasePrimaryKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchPurchaseEntity implements Serializable {

    private static final long serialVersionUID = -7962772940469617081L;

    @Id
    @Column(name = "CD_FILIAL")
    private Integer branchId;

    @Id
    @Column(name = "CD_REGIONAL")
    private Integer regionalDistributionCenterId;

    @Column(name = "FL_COMPRA")
    private Boolean canPurchase;
}
