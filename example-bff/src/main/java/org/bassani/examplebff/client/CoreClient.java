package org.bassani.examplebff.client;

import org.bassani.examplebff.configuration.feign.PurchaseSimulatorCoreOAuth2FeignConfig;
import org.bassani.examplemodellib.domain.dto.ExpirationDTO;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterRequest;
import org.bassani.examplemodellib.domain.dto.ExpirationParameterResponse;
import org.bassani.examplemodellib.domain.dto.SimulationDTO;
import org.bassani.examplemodellib.domain.dto.SimulationPendingApprovalDTO;
import org.bassani.examplemodellib.domain.request.SimulationFollowUpRequest;
import org.bassani.examplemodellib.domain.request.SimulationOrderRequest;
import org.bassani.examplemodellib.domain.request.SimulationPendingApprovalRequest;
import org.bassani.examplemodellib.domain.response.CalculationBasisResponse;
import org.bassani.examplemodellib.domain.response.ProfileResponse;
import org.bassani.examplemodellib.domain.response.SimulationFollowUpResponse;
import org.bassani.examplemodellib.domain.response.SimulationOrderSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationPendingApprovalResponse;
import org.bassani.examplemodellib.domain.response.SimulationProductResponseWrapper;
import org.bassani.examplemodellib.domain.response.SimulationStatusResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryCashCycleResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryDCResponse;
import org.bassani.examplemodellib.domain.response.SimulationSummaryResponse;
import org.bassani.examplemodellib.domain.response.SimulationTemplateResponse;
import org.bassani.examplemodellib.enums.SimulationStatusEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ms-core", url = "${ms-example-core.url}",
        configuration = PurchaseSimulatorCoreOAuth2FeignConfig.class)
public interface CoreClient {

    @PostMapping("/simulations")
    SimulationDTO save(@RequestBody SimulationDTO simulation);

    @GetMapping("/simulations/{simulationId}")
    SimulationDTO getSimulationParameters(@PathVariable Long simulationId);

    @GetMapping("/simulations/{simulationId}/order-summary")
    SimulationOrderSummaryResponse getSimulationOrderSummary(@PathVariable Long simulationId);

    @GetMapping("/simulations/{simulationId}/cash-cycle")
    SimulationSummaryCashCycleResponse getSimulationCashCycle(@PathVariable Long simulationId);

    @GetMapping("/simulations/{simulationId}/template")
    SimulationTemplateResponse getSimulationTemplate(@PathVariable Long simulationId);

    @GetMapping("/simulations/{simulationId}/status")
    SimulationStatusResponse getSimulationStatus(@PathVariable Long simulationId);

    @GetMapping("/simulations/{simulationId}/summary")
    List<SimulationSummaryResponse> getSimulationSummary(@PathVariable Long simulationId);

    @GetMapping("/simulations/{simulationId}/summary-dc")
    List<SimulationSummaryDCResponse> getSimulationSummaryDC(@PathVariable Long simulationId);

    @GetMapping("/calculationBasis")
    List<CalculationBasisResponse> findAllCalculationBasis();

    @PutMapping("/simulations/{id}/status")
    boolean updateSimulationStatus(@PathVariable Long id, @RequestBody SimulationStatusEnum status);

    @GetMapping("/simulations/status")
    List<Long> requestSimulationIdsByStatus(@RequestParam SimulationStatusEnum status,
                                            @RequestParam Long minutesElapsed);

    @PutMapping("/simulations/status")
    void updateSimulationsStatus(@RequestParam SimulationStatusEnum status, List<Long> simulationIds);

    @GetMapping("/profiles/profileName/{profileName}")
    Optional<ProfileResponse> getProfile(@PathVariable String profileName);

    @GetMapping("/status")
    List<SimulationStatusResponse> getAllStatus();

    @PostMapping("/simulations-followup")
    Page<SimulationFollowUpResponse> findAllFollowUp(@RequestBody SimulationFollowUpRequest request,
                                                     @RequestParam Integer page, @RequestParam Integer size);

    @GetMapping("/simulations/{simulationId}/products")
    List<Long> findProductIdBySimulationId(@RequestParam Long simulationId);

    @GetMapping("expirations")
    Page<ExpirationDTO> getExpirations(@RequestParam Pageable pageable);

    @GetMapping("expiration-parameters")
    Page<ExpirationParameterResponse> getExpirationParameters(@RequestParam Pageable pageable);

    @GetMapping("expiration-parameters?latest=1")
    List<ExpirationParameterResponse> findLatestExpirationParameters();

    @GetMapping("expiration-parameters/{id}")
    ExpirationParameterResponse getExpirationParameterById(@PathVariable Long id);

    @PostMapping("expiration-parameters")
    ExpirationParameterResponse saveExpirationParameter(@RequestBody ExpirationParameterRequest request);

    @PostMapping("/simulations-pending-approval")
    Page<SimulationPendingApprovalResponse> findAllByFilter(@RequestBody SimulationPendingApprovalRequest request,
                                                            @RequestParam Integer page, @RequestParam Integer size);

    @GetMapping("/simulations-pending-approval")
    List<SimulationPendingApprovalDTO> getAll();

    @GetMapping("simulations/orders/{id}")
    SimulationProductResponseWrapper getSimulationOrder(@PathVariable Long id);

    @PostMapping("simulations/orders/{id}")
    SimulationProductResponseWrapper createSimulationOrder(@PathVariable Long id,
                                                           @RequestBody SimulationOrderRequest request);
}
