package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.OrderTypes;
import org.bassani.examplebff.repository.OrderTypeRepository;
import org.bassani.examplemodellib.domain.request.param.OrderTypeRequestParams;
import org.bassani.examplemodellib.domain.response.OrderTypeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderTypeServiceTest {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 0;
    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(FIRST_PAGE, DEFAULT_PAGE_SIZE);

    @Mock
    private OrderTypeRepository repo;

    @InjectMocks
    private OrderTypeService service;

    @Test
    void givenValidQueryArgumentInRequest_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithIdOrNameContainingTheQueryArgAreReturned() {
        OrderTypeRequestParams request = new OrderTypeRequestParams("22", null, null);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, 5);

        when(repo.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);
        Page<OrderTypeResponse> actualResponse = service.getOrderTypesPage(request, DEFAULT_PAGEABLE);

        assertIterableEquals(expectedResponse, actualResponse);
    }

    @Test
    void givenValidIdArgumentInRequest_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithIdContainingTheIdArgumentAreReturned() {
        OrderTypeRequestParams request = new OrderTypeRequestParams(null, 2L, null);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, 5);

        when(repo.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);
        Page<OrderTypeResponse> actualResponse = service.getOrderTypesPage(request, DEFAULT_PAGEABLE);

        assertIterableEquals(expectedResponse, actualResponse);
    }

    @Test
    void givenValidNameArgumentInRequest_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithNameContainingTheNameArgumentAreReturned() {
        OrderTypeRequestParams request = new OrderTypeRequestParams(null, null, "Z");
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, 5);

        when(repo.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);
        Page<OrderTypeResponse> actualResponse = service.getOrderTypesPage(request, DEFAULT_PAGEABLE);

        assertIterableEquals(expectedResponse, actualResponse);
    }

    @Test
    void givenNoArgumentsInRequest_whenGetOrderTypesPageIsPerformed_thenAnOrderTypesPageIsReturned() {
        OrderTypeRequestParams noArgsRequest = new OrderTypeRequestParams(null, null, null);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(noArgsRequest, DEFAULT_PAGEABLE, 5);

        when(repo.getOrderTypesPage(noArgsRequest, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);
        Page<OrderTypeResponse> actualResponse = service.getOrderTypesPage(noArgsRequest, DEFAULT_PAGEABLE);

        assertIterableEquals(expectedResponse, actualResponse);
    }
}