package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.bassani.examplemodellib.util.MessageBundle.getMessage;

@Service
@RequiredArgsConstructor
public class SimulationStatusService {

    private final CoreClient coreClient;

    public SimulationStatusResponse getSimulationStatus(Long simulationId) {
        var response = coreClient.getSimulationStatus(simulationId);
        if (response == null) throw new InternalServerErrorException(getMessage("not_generated_simulation_id.message"),
                getMessage("not_generated_simulation_id.description"));
        return response;
    }

    public List<Long> getSimulationIdsByStatus(SimulationStatusEnum status, Long minutesElapsed) {
        return coreClient.requestSimulationIdsByStatus(status, minutesElapsed);
    }

    public void updateSimulationsStatus(SimulationStatusEnum status, List<Long> simulationIds) {
        coreClient.updateSimulationsStatus(status, simulationIds);
    }

    public List<SimulationStatusResponse> getAllStatus() {
        List<SimulationStatusResponse> allStatus = coreClient.getAllStatus();
        if (Optional.ofNullable(allStatus).isPresent()) {
            return allStatus
                    .stream()
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}