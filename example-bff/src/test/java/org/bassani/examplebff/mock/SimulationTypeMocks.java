package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.SimulationTypesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SimulationTypeMocks {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<SimulationTypesResponse> mockedSimulationTypes(){
        return List.of(
                new SimulationTypesResponse(1L, "Antecipação"),
                new SimulationTypesResponse(2L, "Tipo X"),
                new SimulationTypesResponse(3L, "Tipo Z")
        );
    }

    public static String mockedSimulationAsJson(List list) {
        return safeWriteValueAsString(list);
    }

    private static String safeWriteValueAsString(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }
}
