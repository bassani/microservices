package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.MenuMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.MenuService;
import org.bassani.examplemodellib.domain.response.MenuResponse;
import org.bassani.examplemodellib.exception.InvalidIdException;
import org.bassani.examplemodellib.exception.MenuNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest {

    private final String prefixUrl = "/purchases";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private MenuService menuService;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="MENU_GETALL")
    @Test
    void whenGetAllMenusIsPerformed_thenAllMenusAreReturned() throws Exception {
        List<MenuResponse> menus = MenuMocks.mockedMenus();
        MenuResponse menu = menus.get(3);

        when(menuService.getAll()).thenReturn(menus);

        mvc.perform(get(prefixUrl + "/menus").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(menus.size()))
                .andExpect(jsonPath("$[3].name").value(menu.getName()))
                .andExpect(jsonPath("$[3].id").value(menu.getId()))
                .andExpect(jsonPath("$[3].icon").value(menu.getIcon()))
                .andExpect(jsonPath("$[3].items.length()").value(menu.getItems().size()));

    }

    @WithMockUser(roles="MENU_GETALL")
    @Test
    void givenEmptyDBTable_whenGelAllMenusIsPerformed_thenEmptyJSONIsReturned() throws Exception {
        int actualMenuNumber = 0;

        when(menuService.getAll()).thenReturn(Collections.emptyList());

        mvc.perform(get(prefixUrl + "/menus").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(actualMenuNumber));
    }

    @WithMockUser(roles="MENU_GETBYID")
    @Test
    void givenValidId_whenGetMenuByIsPerformed_then200IsResponded() throws Exception {
        int menuId = 10;
        int jsonKeys = MenuResponse.class.getDeclaredFields().length;
        MenuResponse expectedMenu = MenuMocks.mockedMenu(menuId);
        expectedMenu.setItems(MenuMocks.mockedMenuItems(menuId));

        when(menuService.getMenu(menuId)).thenReturn(expectedMenu);

        mvc.perform(get(prefixUrl + "/menus/{menuId}", menuId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(jsonKeys))
                .andExpect(jsonPath("$.name").value(expectedMenu.getName()))
                .andExpect(jsonPath("$.id").value(expectedMenu.getId()))
                .andExpect(jsonPath("$.icon").value(expectedMenu.getIcon()))
                .andExpect(jsonPath("$.items.length()").value(expectedMenu.getItems().size()));
    }

    @WithMockUser(roles="MENU_GETBYID")
    @Test
    void givenNonexistentMenuId_whenGetMenuByIdIsPerformed_then404IsResponded() throws Exception {
        int nonexistentMenuId = 999;
        MenuNotFoundException menuNotFoundException = new MenuNotFoundException();

        when(menuService.getMenu(nonexistentMenuId)).thenThrow(menuNotFoundException);

        mvc.perform(get(prefixUrl + "/menus/{menuId}", nonexistentMenuId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(menuNotFoundException.getMessage()));
    }

    @WithMockUser(roles="MENU_GETBYID")
    @Test
    void givenNonPositiveMenuId_whenGetMenuByIdIsPerformed_then400IsResponded() throws Exception {
        int nonPositiveMenuId = -1;
        InvalidIdException invalidIdException = new InvalidIdException(nonPositiveMenuId);


        mvc.perform(get(prefixUrl + "/menus/{menuId}", nonPositiveMenuId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENU_GETBYID")
    @Test
    void givenNonnumericalMenuId_whenGetMenuByIdIsPerformed_then400IsReturned() throws Exception {
        String nonnumericalMenuId = "Z";
        InvalidIdException invalidIdException = new InvalidIdException(nonnumericalMenuId);

        mvc.perform(get(prefixUrl + "/menus/{menuId}", nonnumericalMenuId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENU_GETBYID")
    @Test
    void givenMenuIdZero_whenGetMenuByIdIsPerformed_then400IsResponded() throws Exception {
        int menuIdZero = 0;
        InvalidIdException invalidIdException = new InvalidIdException(menuIdZero);

        mvc.perform(get(prefixUrl + "/menus/{menuId}", menuIdZero).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }
}