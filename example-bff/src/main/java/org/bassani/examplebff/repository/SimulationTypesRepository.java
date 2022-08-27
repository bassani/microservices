package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorCompositionOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.request.SimulationTypesRequestParams;
import org.bassani.examplemodellib.domain.response.SimulationTypesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "ms-simulation-types", url = "${ms-purchase-composition.url}", configuration = PurchaseSimulatorCompositionOAuth2FeignConfig.class)
public interface SimulationTypesRepository {
    @GetMapping("/simulationTypes")
    List<SimulationTypesResponse> getAll(@SpringQueryMap SimulationTypesRequestParams request);

}
