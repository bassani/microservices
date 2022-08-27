package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationMocks;
import org.bassani.examplemodellib.domain.request.SimulationStatusRequest;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import org.bassani.examplemodellib.exception.InternalServerErrorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.bassani.examplebff.mock.SimulationMocks.mockedSimulationStatusResponse;
import static org.bassani.examplebff.mock.SimulationMocks.validSimulationStatusRequest;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationStatusServiceTest {

    @Mock private CoreClient client;
    @InjectMocks private SimulationStatusService service;

    @Test
    @DisplayName("getSimulationIdsByStatus(SimulationStatusEnum, Long)")
    void shouldGetSimulationIdsWithDefaultStatusAndMinutesElapsed() {
        List<Long> expectedResponse = List.of(10L, 11L, 12L);

        when(client.requestSimulationIdsByStatus(null, null)).thenReturn(expectedResponse);

        List<Long> actualResponse = service.getSimulationIdsByStatus(null, null);

        assertIterableEquals(expectedResponse, actualResponse);
        verify(client).requestSimulationIdsByStatus(null, null);
    }

    @Test
    @DisplayName("getSimulationIdsByStatus(SimulationStatusEnum, Long)")
    void shouldGetSimulationIdsWithErrorStatusAndMinutesElapsed() {
        List<Long> expectedResponse = List.of(10L, 11L, 12L);
        SimulationStatusEnum error = SimulationStatusEnum.ERROR;

        when(client.requestSimulationIdsByStatus(error, null)).thenReturn(expectedResponse);

        List<Long> actualResponse = service.getSimulationIdsByStatus(error, null);

        assertIterableEquals(expectedResponse, actualResponse);
        verify(client).requestSimulationIdsByStatus(error, null);
    }

    @Test
    @DisplayName("getSimulationIdsByStatus(SimulationStatusEnum, Long)")
    void shouldGetSimulationIdsWithDefaultStatusAnd120MinutesElapsed() {
        List<Long> expectedResponse = List.of(10L, 11L, 12L);
        long minutesElapsed = 120L;

        when(client.requestSimulationIdsByStatus(null, minutesElapsed)).thenReturn(expectedResponse);

        List<Long> actualResponse = service.getSimulationIdsByStatus(null, minutesElapsed);

        assertIterableEquals(expectedResponse, actualResponse);
        verify(client).requestSimulationIdsByStatus(null, minutesElapsed);
    }

    @Test
    @DisplayName("updateSimulationsStatus(SimulationStatusEnum, List<Long>)")
    void shouldUpdateSimulationsStatusWithDefaultStatus() {
        List<Long> expectedRequestBody = List.of(10L, 11L, 12L);
        doNothing().when(client).updateSimulationsStatus(null, expectedRequestBody);

        service.updateSimulationsStatus(null, expectedRequestBody);

        verify(client).updateSimulationsStatus(null, expectedRequestBody);
    }

    @Test
    @DisplayName("updateSimulationsStatus(SimulationStatusEnum, List<Long>)")
    void shouldUpdateSimulationsStatusWithDraftStatus() {
        List<Long> expectedRequestBody = List.of(10L, 11L, 12L);
        SimulationStatusEnum draft = SimulationStatusEnum.DRAFT;
        doNothing().when(client).updateSimulationsStatus(draft, expectedRequestBody);

        service.updateSimulationsStatus(draft, expectedRequestBody);

        verify(client).updateSimulationsStatus(draft, expectedRequestBody);
    }

    @Test
    @DisplayName("getSimulationStatus(Long)")
    void givenValidSimulationStatusRequest_thenReturnValidResponse() {
        Long simulationId = 67L;
        SimulationStatusRequest request = validSimulationStatusRequest(simulationId);
        SimulationStatusResponse expectedResponse = mockedSimulationStatusResponse();

        when(client.getSimulationStatus(request.getId())).thenReturn(expectedResponse);

        SimulationStatusResponse actualResponse = service.getSimulationStatus(simulationId);

        assertEquals(expectedResponse, actualResponse);
        verify(client).getSimulationStatus(request.getId());
    }

    @Test
    @DisplayName("getSimulationStatus(Long)")
    void givenEmptySimulationStatusRequest_thenReturnErrorResponse() {
        Long simulationId = 67L;

        when(client.getSimulationStatus(simulationId)).thenReturn(null);

        assertThrows(InternalServerErrorException.class, () -> service.getSimulationStatus(simulationId));
        verifyZeroInteractions(client);
    }

    @WithMockUser(roles="STATUS_GETALL")
    @Test
    @DisplayName("getAll()")
    void shouldGetAllStatus() {
        List<SimulationStatusResponse> statusResponses = SimulationMocks.mockedSimulationStatusResponses();
        SimulationStatusResponse expectedResponse = statusResponses.get(0);

        when(client.getAllStatus()).thenReturn(statusResponses);

        List<SimulationStatusResponse> actualResponse = service.getAllStatus();

        assertEquals(expectedResponse.getId().intValue(), actualResponse.get(0).getId().intValue());
        assertEquals(expectedResponse.getName(), actualResponse.get(0).getName());
        assertEquals(expectedResponse.getActive(), actualResponse.get(0).getActive());

    }

    @WithMockUser(roles="STATUS_GETALL")
    @Test
    @DisplayName("getAll()")
    void shouldGetAnListEmptyOfTheStatus() {
        when(client.getAllStatus()).thenReturn(null);

        List<SimulationStatusResponse> actualResponse = service.getAllStatus();

        assertTrue(actualResponse.isEmpty());

    }

}