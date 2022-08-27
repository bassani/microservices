package org.bassani.examplemodellib.domain.projection;

public interface BaseValueProductPerBranchPurchaseProjection {
    Long getProductId();
    Long getDistributionCenterId();
    Long getSumMaxBaseValue();
}
