package org.bassani.examplemodellib.exception;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationApprovalFlowNotFoundException extends BusinessException {

    public static final String MESSAGE =
            MessageEnum.SIMULATION_APPROVAL_FLOW_NOT_FOUND_EXCEPTION.getMessage();
    public static final String DESCRIPTION =
            MessageEnum.SIMULATION_APPROVAL_FLOW_NOT_FOUND_EXCEPTION.getDescription();

    public SimulationApprovalFlowNotFoundException(Long approvalFlowStatusId) {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(String.format(DESCRIPTION, approvalFlowStatusId));
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        
    }
}
