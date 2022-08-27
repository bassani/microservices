package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorBiOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.request.PurchaseBiRequest;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-bi", url = "${ms-example-bi.url}", configuration =
        PurchaseSimulatorBiOAuth2FeignConfig.class)
public interface PurchaseBiClient {

    @PostMapping("/financial-data")
    List<PurchaseBiResponse> getFinancialData(@RequestBody PurchaseBiRequest purchaseBiRequest);

    @PostMapping("/chart")
    List<PurchaseBiChartResponse> getFinancialChartData(@RequestBody PurchaseBiRequest purchaseBiRequest);

}
