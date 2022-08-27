package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MongoDocNotFoundException extends BusinessException {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final long serialVersionUID = -1563458654391035668L;

    public MongoDocNotFoundException(Long simId, String collection) {
        super(NOT_FOUND, String.format("Couldn't find document %d on MongoDB.", simId),
                String.format("Check existence of Sim ID %d on '%s' collection.", simId, collection));
    }
}
