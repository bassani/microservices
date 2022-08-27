package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimulationTemplateResponse {

    private Double purchaseValue;
    private Double gain;
    private Double totalBudgetValue;
    private Double cashFlowImpact;
    private String orderClassification;
}