package org.bassani.examplemodellib.domain.projection;

import java.math.BigDecimal;

public interface BillingSupplierProjection {

    BigDecimal getMinOrderValue();
    Long getBillingSupplierId();
    String getBillingSupplierName();

}
