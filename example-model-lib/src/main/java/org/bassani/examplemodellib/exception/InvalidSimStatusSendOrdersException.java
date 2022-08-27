package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import org.springframework.http.HttpStatus;

public class InvalidSimStatusSendOrdersException extends BusinessException {

    public static final String MESSAGE = MessageEnum.INVALID_STATUS_SEND_ORDERS.getMessage();
    public static final String DESCRIPTION = MessageEnum.INVALID_STATUS_SEND_ORDERS.getDescription();
    public static final HttpStatus STATUS_CODE = MessageEnum.INVALID_STATUS_SEND_ORDERS.getStatusCode();

    public InvalidSimStatusSendOrdersException(Long simulationId) {
        super.setHttpStatusCode(STATUS_CODE);
        super.setDescription(String.format(DESCRIPTION, simulationId));
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(STATUS_CODE.value()));
    }
}
