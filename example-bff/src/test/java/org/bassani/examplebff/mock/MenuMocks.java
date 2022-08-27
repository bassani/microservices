package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.MenuItemResponse;
import org.bassani.examplemodellib.domain.response.MenuResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class MenuMocks {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<MenuResponse> mockedMenus() {
        MenuResponse simMenu = MenuResponse.builder().id(10).name("Simulador de Pedidos").icon("pi-sliders-h").build();
        simMenu.setItems(mockedMenuItems(simMenu.getId()));
        MenuResponse approvalMenu = MenuResponse.builder().id(20).name("Aprovação | Envio").icon("pi-user").build();
        approvalMenu.setItems(mockedMenuItems(approvalMenu.getId()));
        MenuResponse reportsMenu = MenuResponse.builder().id(30).name("Relatórios").icon("pi-file").build();
        reportsMenu.setItems(mockedMenuItems(reportsMenu.getId()));
        MenuResponse paramMenu = MenuResponse.builder().id(40).name("Cadastro de Parâmetros").icon("pi-cog").build();
        paramMenu.setItems(mockedMenuItems(paramMenu.getId()));

        return List.of(simMenu, approvalMenu, reportsMenu, paramMenu);
    }

    public static MenuResponse mockedMenu(Integer menuId) {
        MenuResponse stock = MenuResponse.builder().id(menuId).name("Qualquer").icon("pi-list").build();
        return mockedMenus().stream().filter(menu -> menu.getId().equals(menuId)).findFirst().orElse(stock);
    }

    private static String safeWriteValueAsString(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }

    public static String mockedMenusAsJson() {
        return safeWriteValueAsString(mockedMenus());
    }

    public static String mockedMenuAsJson(int menuId) {
        MenuResponse menuResponse = mockedMenu(menuId);
        menuResponse.setItems(mockedMenuItems(menuId));
        return safeWriteValueAsString(menuResponse);
    }

    public static List<MenuItemResponse> mockedMenuItems(int menuId) {
        MenuItemResponse sim1 = MenuItemResponse.builder().id(1).name("Simulador").url("/simular").active(true)
                .icon("pi-sliders-h").build();
        MenuItemResponse sim2 = MenuItemResponse.builder().id(2).name("Acompanhar Simulação")
                .url("/acompanhar-simulacao").active(false).icon("pi-list").build();
        MenuItemResponse approval1 = MenuItemResponse.builder().id(3).name("Pendente de aprovação")
                .url("/pendente-de-aprovacao").active(true).icon("pi-clock").build();
        MenuItemResponse approval2 = MenuItemResponse.builder().id(4).name("Aprovadas para envio")
                .url("/aprovadas-para-envio").active(true).icon("pi-check-circle").build();
        MenuItemResponse report = MenuItemResponse.builder().id(5).name("Relatório de Vendas")
                .url("/relatorio-de-vendas").active(false).icon("pi-dollar").build();
        MenuItemResponse forecast1 = MenuItemResponse.builder().id(6).name("Forecast Semanal").url("/forecast-semanal")
                .active(true).icon("pi-tag").build();
        MenuItemResponse forecast2 = MenuItemResponse.builder().id(7).name("Forecast Mensal").url("/forecast-mensal")
                .active(true).icon("pi-tags").build();

        Map<Integer, List<MenuItemResponse>> menuItems = Map.of(10, List.of(sim1, sim2), 20,
                List.of(approval1, approval2), 30, List.of(report), 40, List.of(forecast1, forecast2));

        return menuItems.getOrDefault(menuId, menuItems.get(10));
    }

    private static MenuItemResponse mockedMenuItem(int menuId, int itemId) {
        MenuItemResponse stock = MenuItemResponse.builder().id(itemId).active(false).icon("symbol.svg").name("A menu item").url("/go-to-page").build();
        return mockedMenuItems(menuId).stream().filter(item -> item.getId().equals(itemId)).findFirst().orElse(stock);
    }

    public static String mockedMenuItemsAsJson(int menuId) {
        return safeWriteValueAsString(mockedMenuItems(menuId));
    }

    public static String mockedMenuItemAsJson(int menuId, int menuItemId) {
        return safeWriteValueAsString(mockedMenuItem(menuId, menuItemId));
    }
}