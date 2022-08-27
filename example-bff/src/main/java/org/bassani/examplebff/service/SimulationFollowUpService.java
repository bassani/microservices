package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.request.SimulationFollowUpRequest;
import org.bassani.examplemodellib.domain.response.SimulationFollowUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimulationFollowUpService {
    private final CoreClient coreClient;

    public Page<SimulationFollowUpResponse> getAll(SimulationFollowUpRequest simulationFollowUpRequest,
                                                   Pageable page)  {

        Page<SimulationFollowUpResponse> responsePage;

        simulationFollowUpRequest =
                Optional.ofNullable(simulationFollowUpRequest).orElse(new SimulationFollowUpRequest());

        responsePage = coreClient.findAllFollowUp(simulationFollowUpRequest, page.getPageNumber(), page.getPageSize());
        List<SimulationFollowUpResponse> content = new ArrayList<>();
        long size = 0L;
        if (responsePage != null) {
            content = responsePage.getContent();
            size = responsePage.getTotalElements();
        }

        return new PageImpl<>(content, page, size);
    }

}
