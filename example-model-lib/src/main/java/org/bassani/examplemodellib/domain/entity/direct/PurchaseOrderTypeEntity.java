package org.bassani.examplemodellib.domain.entity.direct;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PEDIDO_COMPRA_TIPO")
@Getter
@Setter
public class PurchaseOrderTypeEntity {

    @Id
    @Column(name = "CD_PEDIDO_COMPRA_TIPO")
    Integer purchaseOrderTypeId;

    @Column(name = "DS_PEDIDO_COMPRA_TIPO")
    String purchaseOrderTypeName;

}
