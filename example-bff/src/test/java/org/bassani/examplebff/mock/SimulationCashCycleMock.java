package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.SimulationCashCycleResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryCashCycleResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SimulationCashCycleMock {
    public static SimulationCashCycleResponse mockedResponse(Long simulationId){
        return SimulationCashCycleResponse.builder()
                .simulationId(simulationId)
                .simulationDate(LocalDate.now())
                .defaultProductUnitPriceSum(124.05)
                .totalLeadTimeWithPrice(1488.6)
                .totalFrequencyTimeWithPrice(1860.75)
                .totalAdditionalFrequencyTimeWithProductUnitPrice(1943.40999)
                .leadTimeWeightedAVGRound(6.3)
                .frequencyTimeWeightedAVGRound(7.0)
                .additionalFrequencyTimeWeightedAVGRound(8.0)
                .regularParamWeightAVG(21.3)
                .purchaseDaysWeightAVGRound(27.9)
                .deltaFrequency(6.6)
                .deltaTerm(5.0)
                .impactCycleDays(-1.6)
                .newTermInDaysQuantity(35)
                .regularTermInDaysQuantity(30)
                .impactCycleReals(BigDecimal.valueOf(0.0))
                .build();
    }

    public static SimulationSummaryCashCycleResponse mockedSummaryResponse(Long simulationId){
        return SimulationSummaryCashCycleResponse.builder()
                .simulationId(simulationId)
                .impactCycleDays(-1.6)
                .impactCycleReals(BigDecimal.valueOf(0.0))
                .negotiateParameter(LocalDate.now())
                .negotiateParameter(LocalDate.now())
                .build();
    }
}
