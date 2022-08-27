package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.SimulationTemplateResponse;

public class SimulationTemplateResponseMocks {

    private SimulationTemplateResponseMocks() {
    }

    public static SimulationTemplateResponse validSimulationTemplateResponse() {
        return SimulationTemplateResponse.builder()
                .purchaseValue(25000d)
                .gain(25d)
                .totalBudgetValue(145000d)
                .cashFlowImpact(12500d)
                .orderClassification("ANTECIPAÇÃO")
                .build();
    }

}