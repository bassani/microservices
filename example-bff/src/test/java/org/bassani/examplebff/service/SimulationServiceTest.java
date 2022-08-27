package org.bassani.examplebff.service;

import org.bassani.examplebff.client.ComposeClient;
import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.request.SimulationStatusRequest;
import org.bassani.examplemodellib.domain.response.SimulationSummaryDCResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.bassani.examplebff.mock.SimulationMocks.mockedSimulationParametersResponse;
import static org.bassani.examplebff.mock.SimulationMocks.validSimulationDtoWithGeneralDiscount;
import static org.bassani.examplebff.mock.SimulationMocks.validSimulationParametersRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationServiceTest {

    @InjectMocks private SimulationService service;

    @Mock private CoreClient coreClient;

    @Mock private ComposeClient compositionClient;

    @Test
    void givenValidRequest_thenReturnValidResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            SimulationDTO request = validSimulationDtoWithGeneralDiscount();
            SimulationDTO expectedResponse = validSimulationDtoWithGeneralDiscount();
            expectedResponse.setId(1L);

            when(coreClient.save(request)).thenReturn(expectedResponse);

            SimulationDTO actualResponse = service.saveParameters(request);

            assertEquals(expectedResponse, actualResponse);
            verify(coreClient, times(1)).save(request);
        }
    }

    @Test
    void givenCoreClientThrowsFeignException_thenReturnErrorResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");
            SimulationDTO request = validSimulationDtoWithGeneralDiscount();
            Class<FeignException.InternalServerError> exception = FeignException.InternalServerError.class;

            when(coreClient.save(request)).thenThrow(exception);
            //action
            assertThrows(exception, () -> service.saveParameters(request));

            //validation
            verify(coreClient, times(1)).save(request);
            verify(compositionClient, times(0)).buildSimulation(any());
        }
    }

    @Test
    void givenSimIdNotGenerated_thenReturnErrorResponse() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDOperador).thenReturn("123");

            SimulationDTO request = validSimulationDtoWithGeneralDiscount();
            Class<InternalServerErrorException> exception = InternalServerErrorException.class;

            when(coreClient.save(request)).thenReturn(validSimulationDtoWithGeneralDiscount());

            assertThrows(exception, () -> service.saveParameters(request));
            verify(coreClient, times(1)).save(request);
            verify(compositionClient, times(0)).buildSimulation(any());
        }
    }

    @Test
    void givenValidGetSimulationRequest_thenReturnValidResponse() {
        Long simulationId = 67L;
        SimulationStatusRequest request = validSimulationParametersRequest(simulationId);
        SimulationDTO expectedResponse = mockedSimulationParametersResponse(simulationId);
        Class<InternalServerErrorException> exception = InternalServerErrorException.class;

        when(coreClient.getSimulationParameters(request.getId())).thenReturn(expectedResponse);

        SimulationDTO actualResponse = service.getSimulationParameters(simulationId);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        verify(coreClient, times(1)).getSimulationParameters(request.getId());
    }

    @Test
    void givenEmptyGetSimulationRequest_thenReturnErrorResponse() {
        Long simulationId = null;
        when(coreClient.getSimulationParameters(any())).thenReturn(null);

        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class,
                () -> service.getSimulationParameters(simulationId));
    }


    @Test
    void givenInvalidSimulationSummaryRequest_thenThrowException() {
        Long simulationId = null;
        when(coreClient.getSimulationSummary(any())).thenReturn(null);

        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class,
                () -> service.getSimulationSummary(simulationId));
    }

    @Test
    void givenValidSimulationSummaryRequest_thenReturnValidResponse() {
        Long simulationId = null;

        List<SimulationSummaryResponse> expectedSummary = SimulationMocks.mockedSummaryList(simulationId);
        when(coreClient.getSimulationSummary(simulationId)).thenReturn(expectedSummary);

        List<SimulationSummaryResponse> actualSummary = service.getSimulationSummary(simulationId);

        assertEquals(expectedSummary, actualSummary);
        verify(coreClient, times(1)).getSimulationSummary(simulationId);
        verifyNoMoreInteractions(coreClient);
    }

    @Test
    void givenInvalidSimulationSummaryDCRequest_thenThrowException() {
        Long simulationId = null;
        when(coreClient.getSimulationSummaryDC(any())).thenReturn(null);

        InternalServerErrorException ex = assertThrows(InternalServerErrorException.class,
                () -> service.getSimulationSummaryDC(simulationId));
    }

    @Test
    void givenValidSimulationSummaryDCRequest_thenReturnValidResponse() {
        Long simulationId = 860L;

        List<SimulationSummaryDCResponse> expectedSummaryDC = SimulationMocks.mockedSummaryDCList(simulationId);
        when(coreClient.getSimulationSummaryDC(simulationId)).thenReturn(expectedSummaryDC);

        List<SimulationSummaryDCResponse> actualSummaryDC = service.getSimulationSummaryDC(simulationId);

        assertEquals(expectedSummaryDC, actualSummaryDC);
        verify(coreClient, times(1)).getSimulationSummaryDC(simulationId);
        verifyNoMoreInteractions(coreClient);
    }
}