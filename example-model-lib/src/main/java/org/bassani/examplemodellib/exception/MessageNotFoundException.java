package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import static br.com.example.purchasesimulatormodellib.enums.MessageEnum.NOTIFICATION_MESSAGE_NOT_FOUND_EXCEPTION;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageNotFoundException extends BusinessException {

    public static final String MESSAGE = NOTIFICATION_MESSAGE_NOT_FOUND_EXCEPTION.getMessage();
    public static final String DESCRIPTION = NOTIFICATION_MESSAGE_NOT_FOUND_EXCEPTION.getDescription();
    public static final HttpStatus HTTP_STATUS_CODE = NOTIFICATION_MESSAGE_NOT_FOUND_EXCEPTION.getStatusCode();

    public MessageNotFoundException(Long messageId) {
        super.setHttpStatusCode(HTTP_STATUS_CODE);
        super.setDescription(String.format(DESCRIPTION, messageId));
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HTTP_STATUS_CODE.value()));

    }
}