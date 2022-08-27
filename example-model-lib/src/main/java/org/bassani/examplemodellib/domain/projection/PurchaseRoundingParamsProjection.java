package org.bassani.examplemodellib.domain.projection;

import java.math.BigDecimal;

public interface PurchaseRoundingParamsProjection {
    Long getProductId();
    Long getQtyMultipleBilling();
    BigDecimal getPercentageRoundingSuggestion();
}
