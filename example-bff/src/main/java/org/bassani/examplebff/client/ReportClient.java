package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorReportsOAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reports", url = "${ms-example-reports.url}", configuration = PurchaseSimulatorReportsOAuth2FeignConfig.class)
public interface ReportClient {

    @GetMapping("/simulations/{id}/export")
    Resource getSpreadsheet(@PathVariable Long id);
}
