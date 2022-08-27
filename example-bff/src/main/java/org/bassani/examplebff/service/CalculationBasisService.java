package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.response.CalculationBasisOptionGroupResponse;
import org.bassani.examplemodellib.domain.response.CalculationBasisOptionResponse;
import org.bassani.examplemodellib.domain.response.CalculationBasisResponse;
import org.bassani.examplemodellib.enums.SalesByPeriodEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.bassani.examplemodellib.mapper.CalculationBasisOptionMapper.calculationBasisOptionMapper;

@Service
@RequiredArgsConstructor
public class CalculationBasisService {

    private static final int CD_DIAS_DE_VENDAS = 1;
    private final CoreClient client;

    public List<CalculationBasisOptionGroupResponse> findAll() {
        var responses = new ArrayList<CalculationBasisOptionGroupResponse>();
        for (CalculationBasisResponse response : client.findAllCalculationBasis()) {
            List<CalculationBasisOptionResponse> options = null;
            if (response.getId() == CD_DIAS_DE_VENDAS) {
                options = Arrays.stream(SalesByPeriodEnum.values()).map(calculationBasisOptionMapper()::enumToOption)
                        .collect(Collectors.toList());
            }
            responses.add(calculationBasisOptionMapper().responseToOptionGroup(response, options));
        }
        return responses;
    }
}