package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorCoreOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.request.ClassificationRequest;
import org.bassani.examplemodellib.domain.response.ClassificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "ms-classification", url = "${ms-example-core.url}/classifications", configuration =
        PurchaseSimulatorCoreOAuth2FeignConfig.class)
public interface ClassificationRepository {

    @GetMapping
    Page<ClassificationResponse> getAll(@SpringQueryMap ClassificationRequest request, Pageable page);
    
    @PostMapping
	ClassificationResponse save(@RequestBody ClassificationRequest classification);
	
	@PutMapping("/{id}")
	ClassificationResponse update(@PathVariable Long id, @RequestBody ClassificationRequest classification);

    @GetMapping("/export")
    Resource getSpreadsheet();
}
