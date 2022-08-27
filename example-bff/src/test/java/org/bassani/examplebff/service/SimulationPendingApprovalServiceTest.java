package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.dto.SimulationPendingApprovalDTO;
import org.bassani.examplemodellib.domain.entity.dbjor.SimulationStatusEntity;
import org.bassani.examplemodellib.domain.request.SimulationPendingApprovalRequest;
import org.bassani.examplemodellib.domain.response.SimulationPendingApprovalResponse;
import org.bassani.examplemodellib.exception.NullParameterException;
import org.bassani.examplemodellib.exception.SimulationPendingApprovalDateBadRequestException;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimulationPendingApprovalServiceTest {

    private final Integer PAGE = 0;
    private final Integer SIZE = 25;
    private final Sort SORT = Sort.by("name").ascending();

    @Mock private CoreClient client;

    @InjectMocks private SimulationPendingApprovalService service;

    @WithMockUser(roles = "STATUS_FINDALL")
    @Test
    void shouldFindAllSimulationsPendingApproval() {
        List<SimulationPendingApprovalResponse> simulationsResponses = List.of(
                SimulationPendingApprovalResponse.builder()
                        .simulationId(1L)
                        .status(SimulationStatusEntity.builder().id(6L).build())
                        .build(), SimulationPendingApprovalResponse.builder()
                        .simulationId(2L)
                        .status(SimulationStatusEntity.builder().id(7L).build())
                        .build(), SimulationPendingApprovalResponse.builder()
                        .simulationId(3L)
                        .status(SimulationStatusEntity.builder().id(8L).build())
                        .build());

        Page<SimulationPendingApprovalResponse> expected = new PageImpl<>(simulationsResponses);
        SimulationPendingApprovalResponse expectedResponse = simulationsResponses.get(0);

        when(client.findAllByFilter(any(SimulationPendingApprovalRequest.class), anyInt(), anyInt())).thenReturn(expected);

        Page<SimulationPendingApprovalResponse> actualResponse = service.findAll(
                mockedSimulationPendingApprovalRequest(), PageRequest.of(PAGE, SIZE, SORT));

        assertEquals(expectedResponse.getSimulationId().intValue(),
                actualResponse.getContent().get(0).getSimulationId().intValue());
        verify(client, times(1)).findAllByFilter(any(SimulationPendingApprovalRequest.class), anyInt(), anyInt());
    }

    @WithMockUser(roles = "STATUS_FINDALL")
    @Test
    void shouldGetAllSimulationsPendingApproval() {
        List<SimulationPendingApprovalDTO> expected = List.of(
                SimulationPendingApprovalDTO.builder().id(1L).name("BLACK FRIDAY").build(),
                SimulationPendingApprovalDTO.builder().id(7L).name("COLABORATIVA").build());

        when(client.getAll()).thenReturn(expected);

        service.getAll();
        verify(client, times(1)).getAll();
    }

    @WithMockUser(roles = "STATUS_FINDALL")
    @Test
    void whenCallFindAllWithRequestNullReturnNullParameterException() {
        assertThrows(NullParameterException.class, () -> service.findAll(null, PageRequest.of(PAGE, SIZE, SORT)));
    }

    @WithMockUser(roles = "STATUS_FINDALL")
    @Test
    void whenCallFindAllWithBadRequestReturnException() {
        assertThrows(SimulationPendingApprovalDateBadRequestException.class,
                () -> service.findAll(mockedSimulationPendingApprovalBadRequest(), PageRequest.of(PAGE, SIZE, SORT)));
    }

    private SimulationPendingApprovalRequest mockedSimulationPendingApprovalRequest() {

        SimulationPendingApprovalRequest request = SimulationPendingApprovalRequest.builder()
                .initialDate(LocalDate.now())
                .finalDate(LocalDate.now())
                .simulationId(1L)
                .supplierIds(List.of(1L, 2L))
                .operatorIds(List.of(1L, 2L))
                .categoryIds(List.of(814l))
                .parentSupplierIds(List.of(2L))
                .build();

        return request;
    }

    private SimulationPendingApprovalRequest mockedSimulationPendingApprovalBadRequest() {

        SimulationPendingApprovalRequest request = SimulationPendingApprovalRequest.builder()
                .initialDate(LocalDate.now())
                .finalDate(LocalDate.now().plusDays(8))
                .simulationId(1L)
                .supplierIds(List.of(1L, 2L))
                .operatorIds(List.of(1L, 2L))
                .categoryIds(List.of(814l))
                .parentSupplierIds(List.of(2L))
                .build();

        return request;
    }
}
