package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.MenuEntity;
import br.com.example.purchasesimulatormodellib.domain.response.MenuResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = MenuItemMapper.class)
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    static MenuMapper menuMapper() {
        return INSTANCE;
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "items", target = "items")
    MenuResponse entityToResponse(MenuEntity entity);

    MenuEntity responseToEntity(MenuResponse response);
}
