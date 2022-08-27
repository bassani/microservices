package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimulationCashCycleResponse {

    private Long simulationId;
    private LocalDate simulationDate;
    private Double deltaFrequency;
    private Double deltaTerm;
    private Integer regularTermInDaysQuantity;
    private Integer newTermInDaysQuantity;
    private Double defaultProductUnitPriceSum;
    private Double totalLeadTimeWithPrice;
    private Double totalFrequencyTimeWithPrice;
    private Double totalAdditionalFrequencyTimeWithProductUnitPrice;
    private Double leadTimeWeightedAVGRound;
    private Double frequencyTimeWeightedAVGRound;
    private Double additionalFrequencyTimeWeightedAVGRound;
    private Double regularParamWeightAVG;
    private Double purchaseDaysWeightAVGRound;
    private Double impactCycleDays;
    private BigDecimal impactCycleReals;
    private LocalDate regularParameter;
    private LocalDate negotiateParameter;
    private String cashCycleImpact;
}
