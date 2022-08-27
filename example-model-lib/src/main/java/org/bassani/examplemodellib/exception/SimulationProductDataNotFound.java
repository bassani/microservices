package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationProductDataNotFound extends BusinessException {

    public static final String MESSAGE = MessageEnum.SIMULATION_FACADE_PRODUCT_DATA_NOT_FOUND.getMessage();
    public static final String DESCRIPTION = MessageEnum.SIMULATION_FACADE_PRODUCT_DATA_NOT_FOUND.getDescription();

    public SimulationProductDataNotFound() {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        log.error("event=exception message={}", MESSAGE);
    }
}
