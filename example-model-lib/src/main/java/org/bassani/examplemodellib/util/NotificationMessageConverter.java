package org.bassani.examplemodellib.util;

import br.com.example.purchasesimulatormodellib.domain.dto.MessageDTO;
import br.com.example.purchasesimulatormodellib.exception.NotificationJSONException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
@ToString
public class NotificationMessageConverter implements AttributeConverter<MessageDTO, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(MessageDTO messageDTO) {

        String docMessageJson = null;
        try {
            docMessageJson = objectMapper.writeValueAsString(messageDTO);
        } catch (JsonProcessingException e) {
            throw new NotificationJSONException();
        }

        return docMessageJson;
    }

    @Override
    public MessageDTO convertToEntityAttribute(String docMessageJSON) {

        MessageDTO messageDTO = null;
        try {
            messageDTO = objectMapper.readValue(docMessageJSON, MessageDTO.class);
        } catch (final IOException e) {
            throw new NotificationJSONException();
        }

        return messageDTO;
    }
}