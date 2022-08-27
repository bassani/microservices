package org.bassani.examplemodellib.util;

import br.com.example.purchasesimulatormodellib.enums.MessageEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JSONUtils {


    public static String toJson(Object objectJson) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(objectJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object readJsonToObject(String jsonArray, Class clazz) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonArray, clazz);
        } catch (Exception e) {
            throw new Exception(MessageEnum.JSON_MAPPING_ERROR_JSONCONVERTER.getMessage());
        }
    }

    public static List<Long> readJsonToLongList(String jsonArray) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonArray, new TypeReference<Object>() {
            });
        } catch (Exception e) {
            throw new Exception(MessageEnum.JSON_MAPPING_ERROR_JSONCONVERTER.getMessage());
        }
    }


}
