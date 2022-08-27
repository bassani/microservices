package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceDictionaryOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.param.OrderTypeRequestParams;
import org.bassani.examplemodellib.domain.response.OrderTypeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-types-service", url = "${ms-example-source-dictionary.url}",
        configuration = PurchaseSimulatorSourceDictionaryOAuthFeignConfig.class)
public interface OrderTypeRepository {

	@GetMapping("/typesorder")
	Page<OrderTypeResponse> getOrderTypesPage(@SpringQueryMap OrderTypeRequestParams request, Pageable pageable);
}
