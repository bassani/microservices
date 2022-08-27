package org.bassani.examplemodellib.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterSimulationNotFoundException extends BusinessException {

    public static final String MESSAGE = "Simulação não encontrada!";
    public static final String DESCRIPTION = "Não foi possível localizar essa Simulação! O id informado não foi " +
            "encontrado no banco de dados. [ID Simulação: %d]";

    public ParameterSimulationNotFoundException(Long simulationId) {
        super.setHttpStatusCode(HttpStatus.NOT_FOUND);
        super.setDescription(String.format(DESCRIPTION, simulationId));
        super.setMessage(MESSAGE);
        super.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        
    }
}