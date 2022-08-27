package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceDictionaryOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.response.DistributionCenterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "distributionCenter", url = "${ms-example-source-dictionary.url}/distributionCenters",
        configuration = PurchaseSimulatorSourceDictionaryOAuthFeignConfig.class)
public interface DistributionCenterRepository {

	@GetMapping
	Page<DistributionCenterResponse> searchAllDistributionCenters(@SpringQueryMap DistributionCenterRequest request, Pageable pageable);

	@GetMapping("/{id}")
	DistributionCenterResponse searchDistributionCenterByID(@PathVariable Long id);

}
