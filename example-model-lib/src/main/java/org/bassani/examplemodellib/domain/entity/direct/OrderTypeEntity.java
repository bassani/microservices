package org.bassani.examplemodellib.domain.entity.direct;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_PEDIDO_COMPRA_TIPO")
@Data
public class OrderTypeEntity implements Serializable {

    @Id
    @Column(name = "CD_PEDIDO_COMPRA_TIPO")
    private Long id;

    @Column(name = "DS_PEDIDO_COMPRA_TIPO", length = 50)
    private String name;
}
