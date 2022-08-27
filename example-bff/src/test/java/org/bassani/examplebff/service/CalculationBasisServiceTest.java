package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.mock.CalculationBasisMocks;
import org.bassani.examplemodellib.domain.response.CalculationBasisOptionGroupResponse;
import org.bassani.examplemodellib.domain.response.CalculationBasisResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.bassani.examplemodellib.mapper.CalculationBasisOptionMapper.calculationBasisOptionMapper;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationBasisServiceTest {

    @Mock
    private CoreClient client;

    @InjectMocks
    private CalculationBasisService service;

    @Test
    void givenRepositoryHasElements_whenFindAllIsPerformed_thenAllElementsAreReturned() {
        List<CalculationBasisOptionGroupResponse> expectedResponses = CalculationBasisMocks.mockedCalculationBasis();
        List<CalculationBasisResponse> entities =
                calculationBasisOptionMapper().optionGroupsToResponses(expectedResponses);

        when(client.findAllCalculationBasis()).thenReturn(entities);
        List<CalculationBasisOptionGroupResponse> actualResponses = service.findAll();

        assertIterableEquals(expectedResponses, actualResponses);
    }

    @Test
    void givenRepositoryHasNoElements_whenFindAllIsPerformed_thenNoElementsAreReturned() {
        List<CalculationBasisResponse> expectedResponses = Collections.emptyList();

        when(client.findAllCalculationBasis()).thenReturn(Collections.emptyList());
        List<CalculationBasisOptionGroupResponse> actualResponses = service.findAll();

        assertIterableEquals(expectedResponses, actualResponses);
    }
}