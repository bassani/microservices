package org.bassani.examplemodellib.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static br.com.example.purchasesimulatormodellib.exception.ExceptionResolver.getRootException;

@Slf4j
public class BadRequestException extends BusinessException {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message, String description, Exception cause) {
        super(BAD_REQUEST, message, description);
        log.error("event=exception message={}", cause == null ? description : getRootException(cause));
    }

    public BadRequestException(String message, String description) {
        this(message, description, null);
    }
}