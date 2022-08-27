package org.bassani.examplemodellib.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class JSONConverter<E> {

    @SuppressWarnings("unchecked")
	public E readJsonToObject(String message, Class<E> clazz) {
        E e = (E) new Object();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            e = objectMapper.readValue(message, clazz);
        } catch (Exception ex) {
            log.error("method=readJsonObject error={}", ExceptionUtils.getRootCause(ex));
        }
        return e;
    }

}