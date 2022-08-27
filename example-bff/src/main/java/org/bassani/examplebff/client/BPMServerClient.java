package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorBPMServerOAuth2FeignConfig;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceWithVariablesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "ms-bpm-server", url = "${ms-example-bpm-server.url}", configuration =
        PurchaseSimulatorBPMServerOAuth2FeignConfig.class)
public interface BPMServerClient {

    @PostMapping("/approval/{simulationId}/start")
    ProcessInstanceWithVariablesDto startApprovalProcessInstance(@PathVariable Long simulationId, @RequestBody Map<String, Object> json);

    @PostMapping("/approval/{simulationId}/complete")
    ResponseEntity<Boolean> completeApprovalTask(@PathVariable Long simulationId, @RequestBody Map<String, Object> json);

}