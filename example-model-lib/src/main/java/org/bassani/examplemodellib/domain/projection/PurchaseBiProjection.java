package org.bassani.examplemodellib.domain.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface PurchaseBiProjection {

    Date getPeriodDayDate();
    Long getProductCode();
    Long getBranchType();
    BigDecimal getCmv();
    BigDecimal getStockValue();

}
