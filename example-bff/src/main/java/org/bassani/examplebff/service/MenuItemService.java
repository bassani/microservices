package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.MenuItemRepository;
import org.bassani.examplemodellib.domain.response.MenuItemResponse;
import org.bassani.examplemodellib.exception.MenuNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Deprecated
@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItemResponse> getAllMenuItems(Integer menuId) {
        List<MenuItemResponse> menuItems = Collections.emptyList();
        try {
            menuItems = menuItemRepository.getAllMenuItems(menuId);
        } catch (FeignException.NotFound notFoundException) {
            throw new MenuNotFoundException();
        }
        return menuItems;
    }

    public MenuItemResponse getMenuItem(Integer menuId, Integer menuItemId) {
        MenuItemResponse menuItem;
        try {
            menuItem = menuItemRepository.getMenuItem(menuId, menuItemId);
        } catch (FeignException.NotFound feignException) {
            throw new MenuNotFoundException();
        }
        return menuItem;
    }
}