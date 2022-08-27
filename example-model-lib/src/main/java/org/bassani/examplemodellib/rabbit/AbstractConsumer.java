package org.bassani.examplemodellib.rabbit;

import br.com.example.purchasesimulatormodellib.exception.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AbstractConsumer<E>  {

    public E convertStringMessageToRequest(String message, Class<E> clazz) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(message, clazz);
        } catch (IOException e) {
            throw new JacksonException();
        }
    }

}
