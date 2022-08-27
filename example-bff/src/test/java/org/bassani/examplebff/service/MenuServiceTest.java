package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.MenuMocks;
import org.bassani.examplebff.repository.MenuRepository;
import org.bassani.examplebff.utils.KeyCloakUtils;
import org.bassani.examplemodellib.domain.response.MenuResponse;
import org.bassani.examplemodellib.exception.MenuNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @WithMockUser(roles="MENU_GETALL")
    void givenMenuRepositoryHasMenus_whenGetAllIsPerformed_thenAllMenusAreReturned() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "4";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDPerfil).thenReturn(expected);
            List<MenuResponse> expectedMenus = MenuMocks.mockedMenus();

            //action
            when(menuRepository.getAllMenusByProfile(anyLong())).thenReturn(expectedMenus);

            //validation
            List<MenuResponse> actualMenus = menuService.getAll();
            assertIterableEquals(expectedMenus, actualMenus);
        }

    }

    @Test
    @WithMockUser(roles="MENU_GETALL")
    void givenMenuRepositoryThrowsFeignException_whenGetAllIsPerformed_ThenFeignExceptionIsThrown() {
        try (MockedStatic<KeyCloakUtils> keyCloakUtilsMockedStatic = Mockito.mockStatic(KeyCloakUtils.class)) {
            //scenario
            String expected = "4";
            keyCloakUtilsMockedStatic.when(KeyCloakUtils::getCDPerfil).thenReturn(expected);
            List<MenuResponse> expectedMenus = MenuMocks.mockedMenus();

            //action
            when(menuRepository.getAllMenusByProfile(anyLong())).thenThrow(FeignException.NotFound.class);

            //validation
            assertThrows(MenuNotFoundException.class, () -> menuService.getAll());
        }

    }

    @Test
    void givenMenuIdIsValid_whenGetMenuIsPerformed_thenAllMenusAreReturned() {
        int menuId = 10;
        MenuResponse expectedMenu = MenuMocks.mockedMenu(menuId);

        when(menuRepository.getMenu(menuId)).thenReturn(expectedMenu);

        MenuResponse actualMenu = menuService.getMenu(menuId);
        assertEquals(expectedMenu, actualMenu);
    }

    @Test
    void givenMenuRepositoryThrowsFeignException_whenGetMenuIsPerformed_ThenFeignExceptionIsThrown() {
        int anyMenuId = 99;

        when(menuRepository.getMenu(anyMenuId)).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> menuService.getMenu(anyMenuId));
    }

    @Test
    void givenNonexistentMenuId_whenGetMenuIsPerformed_ThenMenuNotFoundExceptionIsThrown() {
        int nonexistentMenuId = 99;

        when(menuRepository.getMenu(nonexistentMenuId)).thenThrow(FeignException.NotFound.class);

        assertThrows(MenuNotFoundException.class, () -> menuService.getMenu(nonexistentMenuId));
    }
}