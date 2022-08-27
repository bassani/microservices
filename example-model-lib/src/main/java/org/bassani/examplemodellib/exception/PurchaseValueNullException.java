package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseValueNullException extends BusinessException {

    public static final String MESSAGE = MessageEnum.PURCHASE_VALUE_NULL.getMessage();
    public static final String DESCRIPTION = MessageEnum.PURCHASE_VALUE_NULL.getDescription();

    public PurchaseValueNullException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
