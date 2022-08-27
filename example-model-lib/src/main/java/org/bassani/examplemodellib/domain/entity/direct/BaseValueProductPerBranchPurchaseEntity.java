package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.BaseValueProductPerBranchPurchasePrimaryKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "TB_PRODUTO_VALOR_BASE_FILIAL")
@Entity
@IdClass(BaseValueProductPerBranchPurchasePrimaryKey.class)
@Getter
@Setter
public class BaseValueProductPerBranchPurchaseEntity implements Serializable {

	private static final long serialVersionUID = 3444872262700849732L;

	@Id
    @Column(name = "CD_FILIAL")
    private Integer distributionCenterId;

    @Id
    @Column(name = "CD_PRODUTO")
    private Integer productId;

    
}
