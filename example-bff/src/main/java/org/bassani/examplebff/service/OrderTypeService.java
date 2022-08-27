package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.OrderTypeRepository;
import org.bassani.examplemodellib.domain.request.param.OrderTypeRequestParams;
import org.bassani.examplemodellib.domain.response.OrderTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderTypeService {

    private final OrderTypeRepository repository;

    public Page<OrderTypeResponse> getOrderTypesPage(OrderTypeRequestParams request, Pageable pageable) {
        Page<OrderTypeResponse> orderTypesPage = repository.getOrderTypesPage(request, pageable);
        return new PageImpl<>(orderTypesPage.getContent(), pageable, orderTypesPage.getTotalElements());
    }
}
