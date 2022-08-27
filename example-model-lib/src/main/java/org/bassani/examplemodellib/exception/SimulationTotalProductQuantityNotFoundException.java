package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationTotalProductQuantityNotFoundException extends BusinessException {

    public static final String MESSAGE = MessageEnum.SIMULATION_FACADE_GET_ALL_BRANCHES_NOT_FOUND.getMessage();
    public static final String DESCRIPTION = MessageEnum.SIMULATION_FACADE_GET_ALL_BRANCHES_NOT_FOUND.getDescription();

    public SimulationTotalProductQuantityNotFoundException() {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        
    }
}
