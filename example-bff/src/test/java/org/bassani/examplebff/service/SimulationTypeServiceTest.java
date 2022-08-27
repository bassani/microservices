package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.SimulationTypeMocks;
import org.bassani.examplebff.repository.SimulationTypesRepository;
import org.bassani.examplemodellib.domain.request.SimulationTypesRequestParams;
import org.bassani.examplemodellib.domain.response.SimulationTypesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimulationTypeServiceTest {
    @Mock
    private SimulationTypesRepository simulationTypeRepository;

    @InjectMocks
    private SimulationTypesService simulationTypeService;

    @Test
    public void givensimulationTypesHasList_whenGetAllIsPerformed_thenAllsimulationTypesAreReturned() {
        List<SimulationTypesResponse> expected = SimulationTypeMocks.mockedSimulationTypes();
        when(simulationTypeRepository.getAll(new SimulationTypesRequestParams())).thenReturn(expected);
        List<SimulationTypesResponse> actual = simulationTypeService.getAll(new SimulationTypesRequestParams());

        assertIterableEquals(expected, actual);
    }

    @Test
    public void givensimulationTypesHasList_whenGetAllByQueryIsPerformed_thenAllsimulationTypesAreReturned() {
        List<SimulationTypesResponse> expected = SimulationTypeMocks.mockedSimulationTypes().stream().filter(c -> c.getName().toLowerCase().contains("Antecipação".toLowerCase())).collect(Collectors.toList());
        when(simulationTypeRepository.getAll(new SimulationTypesRequestParams().builder().query("Antecipação").build())).thenReturn(expected);
        List<SimulationTypesResponse> actual = simulationTypeService.getAll(
                SimulationTypesRequestParams.builder().query("Antecipação").build());

        assertIterableEquals(expected, actual);
    }

    @Test
    public void givensimulationTypesHasList_whenGetAllByIdIsPerformed_thenAllsimulationTypesAreReturned() {
        List<SimulationTypesResponse> expected = SimulationTypeMocks.mockedSimulationTypes().stream().filter(c -> c.getId().toString().contains("1")).collect(Collectors.toList());
        when(simulationTypeRepository.getAll(new SimulationTypesRequestParams().builder().id(1L).build())).thenReturn(expected);
        List<SimulationTypesResponse> actual = simulationTypeService.getAll(
                SimulationTypesRequestParams.builder().id(1L).build());

        assertEquals(expected.size(), actual.size());
    }

}
