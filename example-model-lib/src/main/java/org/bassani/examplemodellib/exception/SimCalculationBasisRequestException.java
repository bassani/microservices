package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimCalculationBasisRequestException extends BusinessException {

    public static final String MESSAGE = MessageEnum.SIMULATION_CALCULATION_METHOD_CALCULATION_BASIS.getMessage();
    public static final String DESCRIPTION = MessageEnum.SIMULATION_CALCULATION_METHOD_CALCULATION_BASIS.getDescription();

    public SimCalculationBasisRequestException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
