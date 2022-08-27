package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterGatewayTimeoutException extends BusinessException {

    public static final String MESSAGE = MessageEnum.PARAMETER_GATEWAY_TIMEOUT.getMessage();
    public static final String DESCRIPTION = MessageEnum.PARAMETER_GATEWAY_TIMEOUT.getDescription();

    public ParameterGatewayTimeoutException() {
        super.setHttpStatusCode(HttpStatus.GATEWAY_TIMEOUT);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.GATEWAY_TIMEOUT.value()));
        
    }
}
