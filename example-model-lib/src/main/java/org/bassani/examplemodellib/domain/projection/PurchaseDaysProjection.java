package org.bassani.examplemodellib.domain.projection;

public interface PurchaseDaysProjection {

    Long getSupplierId();

    Long getDistributionCenterId();

    Long getProductId();

    Long getLeadTime();

    Long getAdditionalFrequencyTime();

    Double getCommercialDescPc();

    Long getProductMinPurchaseQt();

    Long getFrequencyTime();

    Double getAverageCost();
}