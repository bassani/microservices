package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class JacksonException extends BusinessException{

    private static final long serialVersionUID = -3832018020087132968L;

    public static final String MESSAGE = MessageEnum.JSON_MAPPING_ERROR_ABSTRACTCONSUMER.getMessage();
    public static final String DESCRIPTION = MessageEnum.JSON_MAPPING_ERROR_ABSTRACTCONSUMER.getDescription();

    public JacksonException() {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(DESCRIPTION);
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
