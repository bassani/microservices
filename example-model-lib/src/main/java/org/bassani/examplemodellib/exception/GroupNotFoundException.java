package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupNotFoundException extends NotFoundException {

    public static final String MESSAGE = MessageEnum.GROUP_NOT_FOUND_EXCEPTION.getMessage();
    public static final String DESCRIPTION = MessageEnum.GROUP_NOT_FOUND_EXCEPTION.getDescription();

    public GroupNotFoundException() {
        super(MESSAGE, DESCRIPTION);
        
    }
}