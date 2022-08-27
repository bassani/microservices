package org.bassani.examplemodellib.domain.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface PurchaseBiChartProjection {

    Date getMonth();
    BigDecimal getCmv();
    BigDecimal getStockValue();
    BigDecimal getCycle();
    BigDecimal getStockDays();

}
