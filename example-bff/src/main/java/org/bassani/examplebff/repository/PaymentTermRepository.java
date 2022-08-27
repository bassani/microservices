package org.bassani.examplebff.repository;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorSourceDictionaryOAuthFeignConfig;
import org.bassani.examplemodellib.domain.request.PaymentTermRequest;
import org.bassani.examplemodellib.domain.response.PaymentTermInstallmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment-term-service", url = "${ms-example-source-dictionary.url}",
        configuration = PurchaseSimulatorSourceDictionaryOAuthFeignConfig.class)
public interface PaymentTermRepository {

    @GetMapping("/paymentTerms")
    Page<PaymentTermInstallmentResponse> searchAllPaymentTerms(@SpringQueryMap PaymentTermRequest request,
                                                               Pageable pageable);

}
