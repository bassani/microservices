package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.MenuMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.MenuItemService;
import org.bassani.examplemodellib.domain.response.MenuItemResponse;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Deprecated
@WebMvcTest(MenuItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuItemControllerTest {

    private final String prefixUrl = "/purchases";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MenuItemService menuItemService;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="MENUITEM_GETALL")
    @Test
    void whenGetMenuItemsIsPerformed_thenAllMenuItemsAreReturned() throws Exception {
        int menuId = 10;
        List<MenuItemResponse> expectedMenuItems = MenuMocks.mockedMenuItems(menuId);
        MenuItemResponse anExpectedMenuItem = expectedMenuItems.get(1);

        when(menuItemService.getAllMenuItems(menuId)).thenReturn(expectedMenuItems);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items", menuId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedMenuItems.size()))
                .andExpect(jsonPath("$[1].id").value(anExpectedMenuItem.getId()))
                .andExpect(jsonPath("$[1].name").value(anExpectedMenuItem.getName()))
                .andExpect(jsonPath("$[1].url").value(anExpectedMenuItem.getUrl()))
                .andExpect(jsonPath("$[1].icon").value(anExpectedMenuItem.getIcon()))
                .andExpect(jsonPath("$[1].active").value(anExpectedMenuItem.getActive()));
    }

    @WithMockUser(roles="MENUITEM_GETALL")
    @Test
    void givenMenuDoesNotExist_whenGetMenuItemsIsPerformed_then404IsReturned() throws Exception {
        int nonexistentMenuId = 999;
        MenuNotFoundException menuNotFoundException = new MenuNotFoundException();

        when(menuItemService.getAllMenuItems(nonexistentMenuId)).thenThrow(menuNotFoundException);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items", nonexistentMenuId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(menuNotFoundException.getMessage()))
                .andExpect(jsonPath("$.description").value(menuNotFoundException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETALL")
    @Test
    void givenNonPositiveMenuId_whenGetMenuItemsIsPerformed_then400IsReturned() throws Exception {
        int nonPositiveMenuId = -1;
        InvalidIdException invalidIdException = new InvalidIdException(nonPositiveMenuId);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items", nonPositiveMenuId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETALL")
    @Test
    void givenNonnumericalMenuId_whenGetMenuItemsIsPerformed_then400IsReturned() throws Exception {
        String nonnumericalId = "Z";
        InvalidIdException invalidIdException = new InvalidIdException(nonnumericalId);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items", nonnumericalId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETBYID")
    @Test
    void whenGetMenuItemIsPerformed_thenAMenuItemIsReturned() throws Exception {
        int menuId = 10;
        int jsonKeys = 5;
        MenuItemResponse menuItem = MenuMocks.mockedMenuItems(menuId).get(0);

        when(menuItemService.getMenuItem(menuId, menuItem.getId())).thenReturn(menuItem);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items/{menuItemId}", menuId, menuItem.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(jsonKeys))
                .andExpect(jsonPath("$.id").value(menuItem.getId()))
                .andExpect(jsonPath("$.name").value(menuItem.getName()))
                .andExpect(jsonPath("$.url").value(menuItem.getUrl()))
                .andExpect(jsonPath("$.icon").value(menuItem.getIcon()))
                .andExpect(jsonPath("$.active").value(menuItem.getActive()));
    }

    @WithMockUser(roles="MENUITEM_GETBYID")
    @Test
    void givenMenuDoesNotExist_whenGetMenuItemIsPerformed_then404IsReturned() throws Exception {
        int nonexistentMenuId = 999;
        int menuItemId = 2;
        MenuNotFoundException menuNotFoundException = new MenuNotFoundException();

        when(menuItemService.getMenuItem(nonexistentMenuId, menuItemId))
                .thenThrow(menuNotFoundException);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items/{menuItemId}", nonexistentMenuId, menuItemId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(menuNotFoundException.getMessage()))
                .andExpect(jsonPath("$.description").value(menuNotFoundException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETBYID")
    @Test
    void givenNonPositiveMenuItemId_whenGetMenuItemIsPerformed_then400IsReturned() throws Exception {
        int menuId = 10;
        int nonPositiveMenuItemId = -1;
        InvalidIdException invalidIdException = new InvalidIdException(nonPositiveMenuItemId);

        mvc.perform(get(prefixUrl + "/menus/{menuId}/items/{menuItemId}", menuId, nonPositiveMenuItemId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETBYID")
    @Test
    void givenNonPositiveMenuId_whenGetMenuItemIsPerformed_then400IsReturned() throws Exception {
        int nonPositiveMenuId = -1;
        int menuItemId = 2;
        InvalidIdException invalidIdException = new InvalidIdException(nonPositiveMenuId);


        mvc.perform(get(prefixUrl + "/menus/{menuId}/items/{menuItemId}", nonPositiveMenuId, menuItemId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETBYID")
    @Test
    void givenNonnumericalMenuItemId_whenGetMenuItemIsPerformed_then400IsReturned() throws Exception {
        int menuId = 10;
        String nonnumericalMenuItemId = "Z";
        InvalidIdException invalidIdException = new InvalidIdException(nonnumericalMenuItemId);

        mvc.perform(get(prefixUrl + "/menus/{nonexistentMenuId}/items/{menuItemId}", menuId, nonnumericalMenuItemId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }

    @WithMockUser(roles="MENUITEM_GETBYID")
    @Test
    void givenNonnumericalMenuId_whenGetMenuItemIsPerformed_then400IsReturned() throws Exception {
        String nonnumericalMenuId = "Z";
        int menuItemId = 10;
        InvalidIdException invalidIdException = new InvalidIdException(nonnumericalMenuId);

        mvc.perform(get(prefixUrl + "/menus/{nonexistentMenuId}/items/{menuItemId}", nonnumericalMenuId, menuItemId)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(invalidIdException.getMessage()))
                .andExpect(jsonPath("$.description").value(invalidIdException.getDescription()));
    }
}