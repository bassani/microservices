package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentNullIDBadRequestException extends BusinessException {

    public static final String MESSAGE = MessageEnum.PAYMENT_TERM_ID_NULL.getMessage();
    public static final String DESCRIPTION = MessageEnum.PAYMENT_TERM_ID_NULL.getDescription();

    public PaymentNullIDBadRequestException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
