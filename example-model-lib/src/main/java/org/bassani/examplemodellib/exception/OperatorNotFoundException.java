package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperatorNotFoundException extends NotFoundException {
    public static final String MESSAGE = "Operador não encontrado!";
    public static final String DESCRIPTION = "Não foi possível localizar esse Operador! O email " +
            "informado não foi encontrado no banco de dados.";

    public OperatorNotFoundException() {
        super(MESSAGE, DESCRIPTION);
        
    }
}