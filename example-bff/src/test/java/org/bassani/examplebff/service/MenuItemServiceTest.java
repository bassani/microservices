package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.MenuMocks;
import org.bassani.examplebff.repository.MenuItemRepository;
import org.bassani.examplemodellib.domain.response.MenuItemResponse;
import org.bassani.examplemodellib.exception.MenuNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Deprecated
@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    void givenValidMenuId_whenGetAllMenuItemsIsPerformed_thenMenuItemsAreReturned() {
        int menuId = 1;
        List<MenuItemResponse> expectedMenuItems = MenuMocks.mockedMenuItems(menuId);

        when(menuItemRepository.getAllMenuItems(menuId)).thenReturn(expectedMenuItems);
        List<MenuItemResponse> actualMenuItems = menuItemService.getAllMenuItems(menuId);

        assertIterableEquals(expectedMenuItems, actualMenuItems);
    }

    @Test
    void givenNonexistentMenuId_whenGetAllMenuItemsIsPerformed_thenMenuNotFoundExceptionIsThrown() {
        int nonexistentMenuId = 999;

        when(menuItemRepository.getAllMenuItems(nonexistentMenuId)).thenThrow(FeignException.NotFound.class);

        assertThrows(MenuNotFoundException.class, () -> menuItemService.getAllMenuItems(nonexistentMenuId));
    }

    @Test
    void givenValidMenuAndMenuItemIds_whenGetMenuItemIsPerformed_thenMenuItemIsReturned() {
        int menuId = 1;
        int menuItemId = 1;
        MenuItemResponse expectedMenuItem = MenuMocks.mockedMenuItems(menuId).get(0);

        when(menuItemRepository.getMenuItem(menuId, menuItemId)).thenReturn(expectedMenuItem);
        MenuItemResponse actualMenuItem = menuItemService.getMenuItem(menuId, menuItemId);

        assertEquals(expectedMenuItem, actualMenuItem);
    }

    @Test
    void givenNonexistentMenuId_whenGetMenuItemIsPerformed_thenMenuNotFoundExceptionIsThrown() {
        int nonexistentMenuId = 999;
        int menuItemId = 1;

        when(menuItemRepository.getMenuItem(nonexistentMenuId, menuItemId)).thenThrow(FeignException.NotFound.class);

        assertThrows(MenuNotFoundException.class,
                () -> menuItemService.getMenuItem(nonexistentMenuId, menuItemId));
    }
}