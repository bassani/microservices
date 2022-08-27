package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPositionAlreadyExistsException extends BusinessException {

    public static final String MESSAGE = MessageEnum.USER_POSITION_ALREADY_EXISTS.getMessage();
    public static final String DESCRIPTION = MessageEnum.USER_POSITION_ALREADY_EXISTS.getDescription();
    public static final HttpStatus HTTP_STATUS = MessageEnum.USER_POSITION_ALREADY_EXISTS.getStatusCode();

    public UserPositionAlreadyExistsException() {
        super.setHttpStatusCode(HTTP_STATUS);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HTTP_STATUS.value()));
    }
}
