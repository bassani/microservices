package org.bassani.examplemodellib.domain.entity.direct;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PEDIDO_COMPRA_STATUS")
@Getter
@Setter
public class PurchaseOrderStatusEntity {

    @Id
    @Column(name = "CD_PEDIDO_COMPRA_STATUS")
    Integer purchaseOrderStatusId;

    @Column(name = "DS_PEDIDO_COMPRA_STATUS")
    String purchaseOrderStatusName;

    @Column(name = "FL_ATIVO")
    Boolean activeFlag;
    
    @Column(name = "FL_TRANSMITIR")
    Boolean transmitFlag;

}
