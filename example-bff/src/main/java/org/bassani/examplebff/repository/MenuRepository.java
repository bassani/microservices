package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorCoreOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.response.MenuResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "menu-service", url = "${ms-example-core.url}", configuration =
        PurchaseSimulatorCoreOAuth2FeignConfig.class)
public interface MenuRepository {

    @GetMapping("/menus")
    List<MenuResponse> getAll();

    @GetMapping("/menus/{id}")
    MenuResponse getMenu(@PathVariable Integer id);

    @GetMapping("/menus/profile/{profileId}")
    List<MenuResponse> getAllMenusByProfile(@PathVariable Long profileId);
}
