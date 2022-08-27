package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.MenuItemEntity;
import br.com.example.purchasesimulatormodellib.domain.response.MenuItemResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuItemMapper {

    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    static MenuItemMapper menuItemMapper() {
        return INSTANCE;
    }

    @Mapping(source = "key.id", target = "id")
    MenuItemResponse entityToResponse(MenuItemEntity entity);

    @InheritInverseConfiguration
    MenuItemEntity responseToEntity(MenuItemResponse response);
}