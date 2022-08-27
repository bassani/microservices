package org.bassani.examplemodellib.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static br.com.example.purchasesimulatormodellib.exception.ExceptionResolver.getRootException;

@Slf4j
public class InternalServerErrorException extends BusinessException {

    private static final HttpStatus SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(String message, String description, Exception cause) {
        super(SERVER_ERROR, message, description);
        log.error("event=exception message={}", cause == null ? description : getRootException(cause));
    }

    public InternalServerErrorException(String message, String description) {
        this(message, description, null);
    }
}