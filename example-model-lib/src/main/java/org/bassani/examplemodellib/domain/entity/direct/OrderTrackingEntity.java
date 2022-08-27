package org.bassani.examplemodellib.domain.entity.direct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PEDIDO_COMPRA_RASTREIO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTrackingEntity {

    @GenericGenerator(
            name = "incrementGenerator",
            strategy = "org.hibernate.id.IncrementGenerator")
    @GeneratedValue(generator="incrementGenerator")
    @Id
    @Column(name = "ID_PEDIDO_COMPRA_RASTREIO")
    private Long orderTrackingId;

    @Column(name = "ID_PEDIDO_COMPRA")
    private Long orderId;

    @Column(name = "DT_DISP_PEDIDO")
    private LocalDateTime availabilityOrderDate;

    public OrderTrackingEntity(Long orderId, LocalDateTime availabilityOrderDate) {
        this.orderId = orderId;
        this.availabilityOrderDate = availabilityOrderDate;
    }

}
