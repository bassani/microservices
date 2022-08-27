package org.bassani.examplemodellib.domain.projection;

public interface GrossProfitMarginProjection {

    Long getPeriodMonthCode();
    Long getProductId();
    String getProductName();
    Long getProductGroupId();
    Long getSupplierId();
    String getSupplierName();
    Double getRbvValue();
    Double getLbcValue();
}
