package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.notify.MessageEntity;
import br.com.example.purchasesimulatormodellib.domain.projection.MessageUserNotificationProjection;
import br.com.example.purchasesimulatormodellib.domain.response.MessageNotificationResponse;
import br.com.example.purchasesimulatormodellib.domain.response.MessageUserNotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = MessageMapper.class)
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    static MessageMapper messageMapper() {
        return INSTANCE;
    }

    @Mapping(target = "expirationDate", source = "message.expirationDate")
    MessageUserNotificationResponse projectionToResponse(MessageUserNotificationProjection projection);
    @Mapping(source = "message.expirationDate", target = "expirationDate")
    MessageNotificationResponse entityToResponse(MessageEntity entity);

    List<MessageNotificationResponse> entityListToResponseList(List<MessageEntity> entityList);

}
