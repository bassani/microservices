package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.OperatorResponse;

import java.util.List;

import static org.bassani.examplemodellib.util.Mocks.asJson;
import static org.bassani.examplemodellib.util.Mocks.jsonKeysFrom;

public class OperatorMocks {

    public static String[] jsonKeys() {
        return jsonKeysFrom(OperatorResponse.class);
    }

    public static List<OperatorResponse> mockedOperators() {
        return List.of(mockedOperatorResponse("My Amazing Name", "myAmazing@email.com", 321L, 123L, 987L),
                mockedOperatorResponse("My Amazing Name2", "myAmazing2@email.com", 2321L, 2123L, 2987L),
                mockedOperatorResponse("My Strong Name", "myStrong@email.com", 2322L, 2125L, 2988L));
    }

    private static OperatorResponse mockedOperatorResponse(String name, String email, Long operatorCode, Long areaCode,
                                                           Long positionCode) {
        return OperatorResponse.builder()
                .name(name)
                .email(email)
                .code(operatorCode)
                .areaCode(areaCode)
                .positionCode(positionCode)
                .build();
    }

    public static String mockedOperatorsAsJson() {
        return asJson(mockedOperators());
    }
    public static String mockedOperatorAsJson() {
        return asJson(mockedOperatorResponse("My Amazing Name", "myAmazing@email.com", 321L, 123L, 987L));
    }
}