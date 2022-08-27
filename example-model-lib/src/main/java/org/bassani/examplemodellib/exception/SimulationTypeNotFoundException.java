package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimulationTypeNotFoundException extends BusinessException {
    public static final String MESSAGE = "Tipo de Simulação não encontrado!";
    public static final String DESCRIPTION = "Não foi possível localizar esse Tipo de Simulação! O id informado não foi encontrado no banco de dados.";

    public SimulationTypeNotFoundException() {
        super(HttpStatus.NOT_FOUND, MESSAGE, DESCRIPTION);
        
    }
}