package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplebff.client.PurchaseBiClient;
import org.bassani.examplemodellib.domain.request.PurchaseBiRequest;
import org.bassani.examplemodellib.domain.response.PurchaseBiChartResponse;
import org.bassani.examplemodellib.domain.response.PurchaseBiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseBiService {

    private final CoreClient coreClient;
    private final PurchaseBiClient biClient;

    public List<Long> findProductIdBySimulationId(Long simulationId) {
        return coreClient.findProductIdBySimulationId(simulationId);
    }

    public List<PurchaseBiResponse> getFinancialData(Long simulationId) {
        List<Long> productIds = coreClient.findProductIdBySimulationId(simulationId);

        if (productIds == null || productIds.isEmpty())
            return new ArrayList<>();

        PurchaseBiRequest purchaseBiRequest = PurchaseBiRequest.builder().productIds(productIds).build();

        return biClient.getFinancialData(purchaseBiRequest);
    }

    public List<PurchaseBiChartResponse> getFinancialChartData(Long simulationId) {
        List<Long> productIds = coreClient.findProductIdBySimulationId(simulationId);

        if (productIds == null || productIds.isEmpty())
            return new ArrayList<>();

        PurchaseBiRequest purchaseBiRequest = PurchaseBiRequest.builder().productIds(productIds).build();

        return biClient.getFinancialChartData(purchaseBiRequest);
    }
}
