package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationCalculationStockCurrentCoverageException extends BusinessException {

    public static final String MESSAGE = MessageEnum.SIMULATION_CALCULATION_STOCK_COVERAGE_CURRENT_BAD_REQUEST.getMessage();
    public static final String DESCRIPTION = MessageEnum.SIMULATION_CALCULATION_STOCK_COVERAGE_CURRENT_BAD_REQUEST.getDescription();

    public SimulationCalculationStockCurrentCoverageException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
