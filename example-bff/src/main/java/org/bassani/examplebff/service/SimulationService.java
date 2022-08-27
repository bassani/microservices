package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.response.SimulationSummaryDCResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.bassani.examplemodellib.util.MessageBundle.getMessage;

@Service
@RequiredArgsConstructor
public class SimulationService {

    private final CoreClient coreClient;

    public SimulationDTO saveParameters(SimulationDTO request) {
        Long cdOperador = Objects.nonNull(KeyCloakUtils.getCDOperador()) ?
                Long.valueOf(KeyCloakUtils.getCDOperador())
                : 1L ; //TODO - precisa ser definido
        request.setOperatorId(cdOperador);
        request.setKeycloakUserId(KeyCloakUtils.getKeycloakUserId());
        var response = coreClient.save(request);
        if (response.getId() == null)
            throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                    getMessage("not_generated_simulation_id.description"));
        return response;
    }

    public SimulationDTO getSimulationParameters(Long simulationId) {
        var response = coreClient.getSimulationParameters(simulationId);
        if (response == null)
            throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                    getMessage("not_generated_simulation_id.description"));
        return response;
    }

    public List<SimulationSummaryResponse> getSimulationSummary(Long simulationId) {
        var response = coreClient.getSimulationSummary(simulationId);
        if (response == null)
            throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                    getMessage("not_generated_simulation_id.description"));
        return response;
    }

    public List<SimulationSummaryDCResponse> getSimulationSummaryDC(Long simulationId) {
        var response = coreClient.getSimulationSummaryDC(simulationId);
        if (response == null)
            throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                    getMessage("not_generated_simulation_id.description"));
        return response;
    }

}