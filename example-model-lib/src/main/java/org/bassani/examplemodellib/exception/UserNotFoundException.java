package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserNotFoundException extends NotFoundException {

    public static final String MESSAGE =
            MessageEnum.USER_NOT_FOUND.getMessage();
    public static final String DESCRIPTION =
            MessageEnum.USER_NOT_FOUND.getDescription();

    public UserNotFoundException() {
        super(MESSAGE, DESCRIPTION);
    }
}
