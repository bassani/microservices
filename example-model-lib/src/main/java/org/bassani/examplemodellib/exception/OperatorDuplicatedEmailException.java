package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperatorDuplicatedEmailException extends BusinessException {
    public static final String MESSAGE = "Email do Operador duplicado!";
    public static final String DESCRIPTION = "Foi encontrado o mesmo email para " + "mais de 1 operador.";

    public OperatorDuplicatedEmailException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        

    }
}