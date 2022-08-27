package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.response.SimulationSummaryCashCycleResponse;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.bassani.examplemodellib.util.MessageBundle.getMessage;

@Service
@RequiredArgsConstructor
public class SimulationCashCycleService {

    private final CoreClient coreClient;

    public SimulationSummaryCashCycleResponse getSimulationCashCycle(Long simulationId) {
        var response = coreClient.getSimulationCashCycle(simulationId);
        if (response == null)
            throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                    getMessage("not_generated_simulation_template.message"));
        return response;
    }

}