package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationCashCycleMock;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.response.SimulationSummaryCashCycleResponse;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationCashCycleServiceTest {

    @InjectMocks private SimulationCashCycleService service;

    @Mock private CoreClient coreClient;

    @Test
    void givenValidSimulationId_thenReturnValidSimulationCashCycleResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Long simulationId = 895l;
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            SimulationSummaryCashCycleResponse simulationSummaryCashCycleResponse =
                    SimulationCashCycleMock.mockedSummaryResponse(simulationId);

            when(coreClient.getSimulationCashCycle(simulationId)).thenReturn(simulationSummaryCashCycleResponse);

            SimulationSummaryCashCycleResponse actualResponse = service.getSimulationCashCycle(simulationId);

            assertEquals(simulationSummaryCashCycleResponse, actualResponse);
            verify(coreClient, times(1)).getSimulationCashCycle(simulationId);
        }
    }

    @Test
    void givenCoreClientThrowsFeignException_thenReturnErrorResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Long simulationId = 895l;
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            Class<FeignException.InternalServerError> exception = FeignException.InternalServerError.class;

            when(coreClient.getSimulationCashCycle(simulationId)).thenThrow(exception);
            //action
            assertThrows(exception, () -> service.getSimulationCashCycle(simulationId));

            //validation
            verify(coreClient, times(1)).getSimulationCashCycle(simulationId);
        }
    }

    @Test
    void givenSimulationId_thenReturnThrowsExceptionResponseWhenSimulationTemplateIsCalled() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Long simulationId = 895l;
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            Class<InternalServerErrorException> exception = InternalServerErrorException.class;

            when(coreClient.getSimulationCashCycle(simulationId)).thenReturn(null);
            //action
            assertThrows(exception, () -> service.getSimulationCashCycle(simulationId));

            //validation
            verify(coreClient, times(1)).getSimulationCashCycle(simulationId);
        }
    }

}