package org.bassani.examplebff.service;

import org.bassani.examplebff.client.ReportClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulationReportService {

    private final ReportClient reportClient;

    public Resource getSpreadsheet(Long simulationId) {
        return reportClient.getSpreadsheet(simulationId);
    }

}
