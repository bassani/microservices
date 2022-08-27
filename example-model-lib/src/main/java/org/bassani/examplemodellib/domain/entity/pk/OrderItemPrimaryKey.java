package org.bassani.examplemodellib.domain.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemPrimaryKey implements Serializable {
    private static final long serialVersionUID = -7965099264369761202L;

    @Column(name = "ID_PEDIDO_COMPRA", nullable = false)
    private Long orderId;

    @Column(name = "NR_SEQUENCIA", nullable = false)
    private Long sequenceNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItemPrimaryKey that = (OrderItemPrimaryKey) o;
        return orderId.equals(that.orderId) && sequenceNumber.equals(that.sequenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, sequenceNumber);
    }
}
