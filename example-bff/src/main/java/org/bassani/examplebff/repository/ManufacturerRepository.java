package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceSupplierOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.ManufacturerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "manufacturer-bff", url = "${ms-example-source-supplier.url}/manufacturers",
        configuration = PurchaseSimulatorSourceSupplierOAuthFeignConfig.class)
public interface ManufacturerRepository {

    @GetMapping
    Page<ManufacturerResponse> getAll(Pageable pageable,
                                      @SpringQueryMap ManufacturerRequest manufacturer);

    @GetMapping
    Page<ManufacturerResponse> getAll(Pageable pageable);

    @GetMapping("/{id}")
    ManufacturerResponse getManufacturer(@PathVariable Integer id);

    @GetMapping("/parents")
    Page<ManufacturerResponse> getAllParentsManufacturers(Pageable pageable,
                                      @SpringQueryMap ManufacturerRequest manufacturer);
}
