package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.CalculationBasisOptionGroupResponse;
import org.bassani.examplemodellib.domain.response.CalculationBasisOptionResponse;

import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.bassani.examplemodellib.util.Mocks.jsonKeysFrom;

public class CalculationBasisMocks {

    public static String[] jsonKeys() {
        return jsonKeysFrom(CalculationBasisOptionGroupResponse.class);
    }

    public static List<CalculationBasisOptionGroupResponse> mockedCalculationBasis() {
        var options = List.of(
                CalculationBasisOptionResponse.builder().name("7 Dias").days(7).build(),
                CalculationBasisOptionResponse.builder().name("15 Dias").days(15).build());
        return List.of(CalculationBasisOptionGroupResponse.builder().name("Dias de vendas").id(1L).options(options)
                        .build(),
                CalculationBasisOptionGroupResponse.builder().name("Forecast semanal").id(2L).build(),
                CalculationBasisOptionGroupResponse.builder().name("Forecast mensal").id(3L).build());
    }

    public static String mockedCalculationBasisAsJson() {
        return asJson(mockedCalculationBasis());
    }
}