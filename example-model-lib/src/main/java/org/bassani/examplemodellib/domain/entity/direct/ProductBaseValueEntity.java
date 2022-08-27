package org.bassani.examplemodellib.domain.entity.direct;

import br.com.example.purchasesimulatormodellib.domain.entity.pk.ProductBaseValuePrimaryKey;
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
@IdClass(ProductBaseValuePrimaryKey.class)
@Getter
@Setter
public class ProductBaseValueEntity implements Serializable {

    @Id
    @Column(name = "CD_PRODUTO")
    Integer productId;

    @Id
    @Column(name = "CD_FILIAL")
    Integer branchId;

    @Column(name = "QT_VALOR_BASE_MAXIMO")
    Integer maxBaseValue;
}
