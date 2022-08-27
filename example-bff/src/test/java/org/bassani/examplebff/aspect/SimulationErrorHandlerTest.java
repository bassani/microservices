package org.bassani.examplebff.aspect;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationErrorHandlerTest {

    @Mock private CoreClient client;
    @InjectMocks private SimulationErrorHandler handler;

    @Test
    @DisplayName("flagErrorOnSimulationStatus(SimulationDTO, Exception)")
    void shouldRequestMsCore() {
        Long simId = 1L;
        SimulationDTO simulationDTO = SimulationMocks.mockedSimulationParametersResponse(simId);

        when(client.updateSimulationStatus(simId, SimulationStatusEnum.ERROR)).thenReturn(true);

        handler.flagErrorOnSimulationStatus(simulationDTO);

        verify(client).updateSimulationStatus(simId, SimulationStatusEnum.ERROR);
    }

    @Test
    @DisplayName("flagErrorOnSimulationStatus(SimulationDTO, Exception)")
    void shouldRequestMsCoreAndHaveTheUpdateFailed() {
        Long simId = 1L;
        SimulationDTO simulationDTO = SimulationMocks.mockedSimulationParametersResponse(simId);

        when(client.updateSimulationStatus(simId, SimulationStatusEnum.ERROR)).thenReturn(false);

        handler.flagErrorOnSimulationStatus(simulationDTO);

        verify(client).updateSimulationStatus(simId, SimulationStatusEnum.ERROR);
    }

    @Test
    @DisplayName("flagErrorOnSimulationStatus(SimulationDTO, Exception)")
    void shouldNotRequestMsCore() {
        SimulationDTO simulationDTO = SimulationMocks.validSimulationDtoWithGeneralDiscount();

        handler.flagErrorOnSimulationStatus(simulationDTO);

        verifyZeroInteractions(client);
    }
}