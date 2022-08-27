package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyCloakGroupNotFoundException extends BusinessException {
    public static final String MESSAGE = "Grupo não encontrado!";
    public static final String DESCRIPTION = "Não foi possível localizar esse grupo! O id informado não foi encontrado no keyCloak.";

    public KeyCloakGroupNotFoundException() {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        
    }
}