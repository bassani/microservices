package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDaysResponse {

    private Long supplierId;

    private Long distributionCenterId;

    private Long productId;

    private Long leadTime;

    private Long additionalFrequencyTime;

    private Double commercialDescPc;

    private Long productMinPurchaseQt;

    private Long frequencyTime;

    private Double averageCost;
}
