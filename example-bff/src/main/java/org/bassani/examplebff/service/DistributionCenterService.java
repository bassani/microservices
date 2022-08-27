package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.DistributionCenterRepository;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.response.DistributionCenterResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DistributionCenterService {

    private final DistributionCenterRepository repository;

    public Page<DistributionCenterResponse> getAllDistributionCenters(DistributionCenterRequest request, Pageable pageable) {
        Page<DistributionCenterResponse> response = repository.searchAllDistributionCenters(request, pageable);
        return new PageImpl<>(response.getContent(), pageable, response.getTotalElements());
    }

    public DistributionCenterResponse getDistributionCenterById(Long id) {
        return repository.searchDistributionCenterByID(id);
    }


}
