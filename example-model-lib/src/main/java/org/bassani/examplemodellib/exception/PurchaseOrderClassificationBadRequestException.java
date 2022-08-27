package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseOrderClassificationBadRequestException extends BusinessException {

	public static final String MESSAGE = "Alteração não pode ser feita";
    public static final String DESCRIPTION = "Nome, não pode ser modificado ou já existe no banco!";

    public PurchaseOrderClassificationBadRequestException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
