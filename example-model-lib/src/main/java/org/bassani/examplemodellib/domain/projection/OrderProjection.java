package org.bassani.examplemodellib.domain.projection;

public interface OrderProjection {

    Long getDistributionCenterId();
    Long getProductId();
    Long getQuantityPendingOrders();
}
