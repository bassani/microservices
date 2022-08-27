package org.bassani.examplebff.aspect;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SimulationErrorHandler {

    private final CoreClient core;

    @AfterThrowing(pointcut = "@annotation(org.bassani.examplemodellib.configuration.SimulationErrorWatcher)" +
            " && args(request)")
    public void flagErrorOnSimulationStatus(SimulationDTO request) {
        if (request.getId() == null) {
            log.warn("event=warn message=Annotated method arg haven't got sim ID, so rethrowing original exception.");
            return;
        }
        requestSimulationStatusUpdate(request.getId());
    }

    private void requestSimulationStatusUpdate(Long simId) {
        log.info("event=info message=Requesting status update for simulation ID {}.", simId);
        boolean hasUpdated = core.updateSimulationStatus(simId, SimulationStatusEnum.ERROR);
        if (!hasUpdated)
            log.error("event=error message=Couldn't update simulation {}'s status to ERROR, rethrowing original " +
                            "exception.", simId);
    }
}