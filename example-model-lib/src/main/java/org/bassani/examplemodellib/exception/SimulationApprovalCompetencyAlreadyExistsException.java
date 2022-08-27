package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationApprovalCompetencyAlreadyExistsException extends BusinessException {

    public static final String MESSAGE =
            MessageEnum.SIMULATION_APPROVAL_COMPETENCY_ALREADY_EXISTS_EXCEPTION.getMessage();
    public static final String DESCRIPTION =
            MessageEnum.SIMULATION_APPROVAL_COMPETENCY_ALREADY_EXISTS_EXCEPTION.getDescription();

    public SimulationApprovalCompetencyAlreadyExistsException(String profile) {
        super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        super.setDescription(String.format(DESCRIPTION, profile));
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        
    }
}
