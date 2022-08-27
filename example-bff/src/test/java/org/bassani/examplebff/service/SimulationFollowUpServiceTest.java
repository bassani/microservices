package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.SimulationFollowUpMock;
import org.bassani.examplemodellib.domain.request.SimulationFollowUpRequest;
import org.bassani.examplemodellib.domain.response.SimulationFollowUpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimulationFollowUpServiceTest {

    private final Integer PAGE = 0;
    private final Integer SIZE = 25;
    private final Sort SORT = Sort.by("name").ascending();

    @Mock
    private CoreClient client;

    @InjectMocks
    private SimulationFollowUpService service;

    @WithMockUser(roles="STATUS_GETALL")
    @Test
    @DisplayName("getAll()")
    void shouldGetAllSimulationsFollowUp() {
        List<SimulationFollowUpResponse> simulationsResponses = SimulationFollowUpMock.mockedSimulationFollowUp();
        Page<SimulationFollowUpResponse> expected = new PageImpl<>(simulationsResponses);
        SimulationFollowUpResponse expectedResponse = simulationsResponses.get(0);

        when(client.findAllFollowUp(any(SimulationFollowUpRequest.class), anyInt(), anyInt())).thenReturn(expected);

        Page<SimulationFollowUpResponse> actualResponse =
                service.getAll(SimulationFollowUpMock.mockedSimulationRequestId(1L), PageRequest.of(PAGE
                , SIZE, SORT));

        assertEquals(expectedResponse.getId().intValue(), actualResponse.getContent().get(0).getId().intValue());
        verify(client, times(1)).findAllFollowUp(any(SimulationFollowUpRequest.class), anyInt(), anyInt());
    }

    @WithMockUser(roles="STATUS_GETALL")
    @Test
    @DisplayName("getAll()")
    void shouldGetAnListEmptyOfTheSimulationFollowUp() {

        when(client.findAllFollowUp(any(SimulationFollowUpRequest.class), anyInt(), anyInt())).thenReturn(null);

        Page<SimulationFollowUpResponse> actualResponse = service.getAll(null, PageRequest.of(PAGE, SIZE, SORT));

        assertTrue(actualResponse.isEmpty());
        verify(client, times(1)).findAllFollowUp(any(SimulationFollowUpRequest.class), anyInt(), anyInt());
    }


}
