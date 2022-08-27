package org.bassani.examplebff.service;

import org.bassani.examplebff.client.SourceOperatorClient;
import org.bassani.examplemodellib.domain.request.OperatorRequest;
import org.bassani.examplemodellib.domain.response.OperatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceOperatorService {

    private final SourceOperatorClient sourceOperatorClient;

    public Page<OperatorResponse> getAll(OperatorRequest request, Pageable pageable) {
        Page<OperatorResponse> page = sourceOperatorClient.getAll(request, pageable);
        return new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
    }
}
