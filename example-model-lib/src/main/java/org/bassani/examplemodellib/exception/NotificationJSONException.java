package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationJSONException extends BusinessException{

    private static final long serialVersionUID = 2038297428518720546L;

    public static final String MESSAGE = MessageEnum.NOTIFY_JSON_MAPPING_ERROR_NOT_FOUND.getMessage();
    public static final String DESCRIPTION = MessageEnum.NOTIFY_JSON_MAPPING_ERROR_NOT_FOUND.getDescription();

    public NotificationJSONException() {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        
    }
}
