package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class InvalidIdException extends BusinessException {

    private static final String MESSAGE = "ID inválido";
    private static final String DESCRIPTION = "Verifique o ID ";

    public InvalidIdException(String id) {
        super(HttpStatus.BAD_REQUEST, MESSAGE, DESCRIPTION + id);
        
    }

    public InvalidIdException(int id) {
        this(String.valueOf(id));
    }
}
