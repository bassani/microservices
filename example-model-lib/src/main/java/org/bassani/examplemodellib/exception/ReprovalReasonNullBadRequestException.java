package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReprovalReasonNullBadRequestException extends BusinessException {

    public static final String MESSAGE = MessageEnum.REPROVAL_REASON_NULL_EXCEPTION.getMessage();
    public static final String DESCRIPTION = MessageEnum.REPROVAL_REASON_NULL_EXCEPTION.getDescription();

    public ReprovalReasonNullBadRequestException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));

    }
}
