package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorCompositionOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.response.SubCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-composition", url = "${ms-purchase-composition.url}", configuration =
        PurchaseSimulatorCompositionOAuth2FeignConfig.class)
public interface ComposeClient {

    @PostMapping("/simulations")
    void buildSimulation(@RequestBody SimulationDTO simulationResponse);

    @GetMapping("/products/categories/{id}/subcategories")
    Page<SubCategoryResponse> getSubcategoriesByCategory(@PathVariable Long id, Pageable page);
}