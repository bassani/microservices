package org.bassani.examplemodellib.domain.projection;

import br.com.example.purchasesimulatormodellib.domain.dto.MessageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface MessageUserNotificationProjection {

    Long getId();
    MessageDTO getMessage();
    LocalDateTime getNotificationDate();
    String getKeycloakUserId();
    Boolean getRead();
    @Value("#{target.message.expirationDate}")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getExpirationDate();
}
