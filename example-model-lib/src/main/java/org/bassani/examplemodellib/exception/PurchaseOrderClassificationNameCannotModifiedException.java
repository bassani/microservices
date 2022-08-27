package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseOrderClassificationNameCannotModifiedException extends BusinessException {

	public static final String MESSAGE = "Nome não pode ser modificado";
    public static final String DESCRIPTION = "Nome não pode ser modificado!";

    public PurchaseOrderClassificationNameCannotModifiedException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
