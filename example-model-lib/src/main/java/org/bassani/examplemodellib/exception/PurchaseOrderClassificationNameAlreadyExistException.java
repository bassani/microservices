package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseOrderClassificationNameAlreadyExistException extends BusinessException {

	public static final String MESSAGE = "Nome já existe";
    public static final String DESCRIPTION = "Nome da classificação de compra já existe!";

    public PurchaseOrderClassificationNameAlreadyExistException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
