package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationOrderSummaryResponseMocks;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.response.SimulationOrderSummaryResponse;
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
class SimulationOrderSummaryServiceTest {

    @InjectMocks private SimulationOrderSummaryService service;

    @Mock private CoreClient coreClient;

    @Test
    void givenValidSimulationId_thenReturnValidSimulationOrderSummaryResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Long simulationId = 895l;
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            SimulationOrderSummaryResponse expectedOrderSummaryResponse =
                    SimulationOrderSummaryResponseMocks.validSimulationOrderSummaryResponse();

            when(coreClient.getSimulationOrderSummary(simulationId)).thenReturn(expectedOrderSummaryResponse);

            SimulationOrderSummaryResponse actualResponse = service.getSimulationOrderSummary(simulationId);

            assertEquals(expectedOrderSummaryResponse, actualResponse);
            verify(coreClient, times(1)).getSimulationOrderSummary(simulationId);
        }
    }

    @Test
    void givenCoreClientThrowsFeignException_thenReturnErrorResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Long simulationId = 895l;
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            Class<FeignException.InternalServerError> exception = FeignException.InternalServerError.class;

            when(coreClient.getSimulationOrderSummary(simulationId)).thenThrow(exception);
            //action
            assertThrows(exception, () -> service.getSimulationOrderSummary(simulationId));

            //validation
            verify(coreClient, times(1)).getSimulationOrderSummary(simulationId);
        }
    }

    @Test
    void givenSimulationId_thenReturnThrowsExceptionResponseWhenSimulationTemplateIsCalled() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            Long simulationId = 895l;
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            Class<InternalServerErrorException> exception = InternalServerErrorException.class;

            when(coreClient.getSimulationOrderSummary(simulationId)).thenReturn(null);
            //action
            assertThrows(exception, () -> service.getSimulationOrderSummary(simulationId));

            //validation
            verify(coreClient, times(1)).getSimulationOrderSummary(simulationId);
        }
    }

}