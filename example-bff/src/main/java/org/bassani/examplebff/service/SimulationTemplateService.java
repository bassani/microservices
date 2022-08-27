package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.response.SimulationTemplateResponse;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.bassani.examplemodellib.util.MessageBundle.getMessage;

@Service
@RequiredArgsConstructor
public class SimulationTemplateService {

    private final CoreClient coreClient;

    public SimulationTemplateResponse getSimulationTemplate(Long simulationId) {
        var response = coreClient.getSimulationTemplate(simulationId);
        if (response == null)
            throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                    getMessage("not_generated_simulation_template.message"));
        return response;
    }

}