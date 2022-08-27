package org.bassani.exampleorder.service;

import org.bassani.examplemodellib.domain.request.OrderParamRequest;
import org.bassani.examplemodellib.domain.request.OrderRequest;
import org.bassani.examplemodellib.domain.response.OrderParamResponse;
import org.bassani.examplemodellib.exception.NullParameterException;
import org.bassani.examplemodellib.exception.SimulationPendingApprovalDateBadRequestException;
import org.bassani.exampleorder.repository.OrderRepository;
import org.bassani.exampleorder.repository.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.bassani.examplemodellib.mapper.OrderMapper.orderMapper;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<OrderParamResponse> findAll(OrderParamRequest request, Pageable pageable) {
        inputValidation(request);
        Page<OrderParamResponse> orderResponsePage = orderRepository.findAll(
                        new OrderSpecification(request), pageable)
                     .map(orderMapper()::entityToResponse);

        List<OrderParamResponse> content = orderResponsePage.getContent();

        return new PageImpl<>(content, pageable, orderResponsePage.getTotalElements());
    }

    private void inputValidation(OrderParamRequest request) {
        if (isNull(request) || (isNull(request.getInitialDate()) && isNull(request.getOrderNumber()) && isNull((request.getOrderId())))) {
            throw new NullParameterException();
        }

        if(!isNull(request.getFinalDate())
                && ChronoUnit.DAYS.between(request.getInitialDate(), request.getFinalDate()) > 7L) {
            throw new SimulationPendingApprovalDateBadRequestException();
        }

    }

    public OrderParamResponse saveOrder(OrderRequest request) {
        request.setOrderId(orderRepository.getSequenceOrderId());
        request.setOrderNumber(orderRepository.getSequenceOrderNumber());
        request.setPurchaseReportNumber(orderRepository.getSequencePurchaseReportNumber());

        var order = orderMapper().requestToEntity(request);
        order.getItems().forEach(orderItemEntity -> orderItemEntity.getId().setOrderId(request.getOrderId()));

        return orderMapper().entityToResponse(orderRepository.save(order));
    }

}
