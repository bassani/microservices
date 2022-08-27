package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBadRequestException extends BusinessException {

    public UserBadRequestException(MessageEnum messageEnum) {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(messageEnum.getDescription());
        super.setMessage(messageEnum.getMessage());
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
