package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceSupplierOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.BillingSupplierToPurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "billingSupplier", url = "${ms-example-source-supplier.url}",
        configuration = PurchaseSimulatorSourceSupplierOAuthFeignConfig.class)
public interface BillingSupplierRepository {

    @GetMapping("/billing")
    List<BillingSupplierToPurchaseResponse> getAllBillingSupplier(Pageable pageable,
                                                                  @SpringQueryMap ManufacturerRequest manufacturer);

}
