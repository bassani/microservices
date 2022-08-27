package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistributionCenterNotFoundException extends NotFoundException {

	public static final String MESSAGE = "Centro de Distribuição não encontrado!";
    public static final String DESCRIPTION = "Verifique o ID";

    public DistributionCenterNotFoundException() {
        super(MESSAGE, DESCRIPTION);
        
    }
}
