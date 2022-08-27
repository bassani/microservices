package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationCalculationProductNotFoundException extends BusinessException {

    public static final String MESSAGE = MessageEnum.SIMULATION_CALCULATION_PRODUCT_ID_NOT_FOUND.getMessage();
    public static final String DESCRIPTION = MessageEnum.SIMULATION_CALCULATION_PRODUCT_ID_NOT_FOUND.getDescription();

    public SimulationCalculationProductNotFoundException() {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        
    }
}
