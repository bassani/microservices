package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorCompositionOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.response.MenuItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Deprecated
@FeignClient(name = "menu-item-service", url = "${ms-purchase-composition.url}", configuration = PurchaseSimulatorCompositionOAuth2FeignConfig.class)
public interface MenuItemRepository {

    @GetMapping("/menus/{menuId}/items")
    List<MenuItemResponse> getAllMenuItems(@PathVariable Integer menuId);

    @GetMapping("/menus/{menuId}/items/{itemId}")
    MenuItemResponse getMenuItem(@PathVariable Integer menuId, @PathVariable Integer itemId);
}
