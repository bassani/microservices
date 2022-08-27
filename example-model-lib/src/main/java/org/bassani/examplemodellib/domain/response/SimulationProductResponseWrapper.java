package org.bassani.examplemodellib.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimulationProductResponseWrapper {

    private Long totalOrderUnits;

    private Double totalOrderValue;

    private LocalDate simulationDate;

    private String fiscalOperation;

    private List<SimulationProductResponse> products;

}