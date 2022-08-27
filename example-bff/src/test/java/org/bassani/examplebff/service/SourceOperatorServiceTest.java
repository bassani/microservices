package org.bassani.examplebff.service;

import org.bassani.examplebff.client.SourceOperatorClient;
import org.bassani.examplebff.mock.OperatorMocks;
import org.bassani.examplemodellib.domain.request.OperatorRequest;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SourceOperatorServiceTest {

    @InjectMocks
    private SourceOperatorService sourceOperatorService;

    @Mock
    private SourceOperatorClient sourceOperatorClient;

    @Test
    void givenOperator_whenGetAllIsPerformed_thenAllOperatorsAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<OperatorResponse> expected = new PageImpl<>(OperatorMocks.mockedOperators());

        Page<OperatorResponse> page = new PageImpl<>(expected.getContent());

        when(sourceOperatorClient.getAll(new OperatorRequest(), pageable)).thenReturn(page);
        Page<OperatorResponse> actual = sourceOperatorService.getAll(OperatorRequest.builder().build(),
                pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    void givenOperator_whenGetAllByQueryIsPerformed_thenAllOperatorsAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<OperatorResponse> expected = new PageImpl<>(
                OperatorMocks.mockedOperators().stream().filter(c -> c.getName().toLowerCase().contains(
                        "STRONG".toLowerCase())).collect(
                        Collectors.toList()));

        Page<OperatorResponse> page = new PageImpl<>(expected.getContent());

        when(sourceOperatorClient.getAll(new OperatorRequest().builder().name("strong").build(), pageable)).thenReturn(page);
        Page<OperatorResponse> actual =
                sourceOperatorService.getAll(OperatorRequest.builder().name("strong").build(),
                pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    void givenOperator_whenGetAllByNameIsPerformed_thenNoneOperatorAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<OperatorResponse> expected = new PageImpl<>(
                OperatorMocks.mockedOperators().stream().filter(c -> c.getName().toLowerCase().contains(
                        "MANAGER".toLowerCase())).collect(
                        Collectors.toList()));

        Page<OperatorResponse> page = new PageImpl<>(expected.getContent());

        when(sourceOperatorClient.getAll(new OperatorRequest().builder().name("manager").build(), pageable)).thenReturn(page);
        Page<OperatorResponse> actual =
                sourceOperatorService.getAll(OperatorRequest.builder().name("manager").build(),
                        pageable);

        assertIterableEquals(expected.getContent(), actual);
    }
}
