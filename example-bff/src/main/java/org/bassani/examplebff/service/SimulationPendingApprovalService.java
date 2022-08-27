package org.bassani.examplebff.service;

import org.bassani.examplebff.client.CoreClient;
import org.bassani.examplemodellib.domain.dto.SimulationPendingApprovalDTO;
import org.bassani.examplemodellib.domain.request.SimulationPendingApprovalRequest;
import org.bassani.examplemodellib.domain.response.SimulationPendingApprovalResponse;
import org.bassani.examplemodellib.exception.NullParameterException;
import org.bassani.examplemodellib.exception.SimulationPendingApprovalDateBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class SimulationPendingApprovalService {

    private final CoreClient coreClient;

    @Transactional(readOnly = true)
    public Page<SimulationPendingApprovalResponse> findAll(SimulationPendingApprovalRequest request, Pageable page)  {
        inputValidation(request);
        Page<SimulationPendingApprovalResponse> responsePage;

        responsePage = coreClient.findAllByFilter(request, page.getPageNumber(), page.getPageSize());
        List<SimulationPendingApprovalResponse> content = new ArrayList<>();
        long size = 0L;
        if (responsePage != null) {
            content = responsePage.getContent();
            size = responsePage.getTotalElements();
        }

        return new PageImpl<>(content, page, size);
    }

    private void inputValidation(SimulationPendingApprovalRequest request) {
        if (isNull(request) || isNull(request.getInitialDate())) {
            throw new NullParameterException();
        }

        if (!isNull(request.getFinalDate()) && ChronoUnit.DAYS.between(request.getInitialDate(), request.getFinalDate()) > 7L) {
            throw new SimulationPendingApprovalDateBadRequestException();
        }
    }

    @Transactional(readOnly = true)
    public List<SimulationPendingApprovalDTO> getAll() {
        return coreClient.getAll();
    }
}
