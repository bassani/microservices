package org.bassani.examplemodellib.domain.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CashCycleCalculationEntity {

    @Id
    private String id;
    private Long simulationId;
    private LocalDate simulationDate;
    private Double defaultProductUnitPriceSum;
    private Double totalLeadTimeWithPrice;
    private Double totalFrequencyTimeWithPrice;
    private Double totalAdditionalFrequencyTimeWithProductUnitPrice;
    private Double leadTimeWeightedAVGRound;
    private Double frequencyTimeWeightedAVGRound;
    private Double additionalFrequencyTimeWeightedAVGRound;
    private Double regularParamWeightAVG;
    private Double purchaseDaysWeightAVGRound;
    private Long deltaFrequency;
    private Long deltaTerm;
    private Long impactCycleDays;

}
