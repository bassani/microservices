package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotFoundException extends BusinessException {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    public NotFoundException(String message, String description) {
        super(NOT_FOUND, message, description);
    }
}
