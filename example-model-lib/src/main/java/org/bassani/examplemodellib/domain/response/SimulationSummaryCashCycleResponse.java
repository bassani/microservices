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
public class SimulationSummaryCashCycleResponse {

    private Long simulationId;
    private Double impactCycleDays;
    private BigDecimal impactCycleReals;
    private LocalDate regularParameter;
    private LocalDate negotiateParameter;
    private String cashCycleImpact;
}
