package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.OrderTypes;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.OrderTypeService;
import org.bassani.examplemodellib.domain.request.param.OrderTypeRequestParams;
import org.bassani.examplemodellib.domain.response.OrderTypeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(OrderTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderTypeControllerTest {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 0;
    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(FIRST_PAGE, DEFAULT_PAGE_SIZE);
    private static final String URL = "/purchases/typesorder";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderTypeService orderTypeService;
    @MockBean private KeyCloakService keyCloakService;

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenThereAreElevenOrderTypes_whenGetOrderTypesPageIsPerformed_thenTheFirstPageIsReturnedAndAnotherOneIsAvailable()
            throws Exception {
        int total = 11;
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(DEFAULT_PAGEABLE, total);
        OrderTypeResponse expectedFirstOrderType = expectedResponse.iterator().next();

        when(orderTypeService.getOrderTypesPage(any(), eq(DEFAULT_PAGEABLE))).thenReturn(expectedResponse);

        mvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(DEFAULT_PAGE_SIZE))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(false))
                .andExpect(jsonPath("$.content[0].id").value(expectedFirstOrderType.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedFirstOrderType.getName()));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenThereAreSixOrderTypes_whenGetOrderTypesPageIsPerformed_thenTheFirstAndOnlyPageIsReturned()
            throws Exception {
        int total = 6;
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(DEFAULT_PAGEABLE, total);
        OrderTypeResponse expectedFirstOrderType = expectedResponse.iterator().next();

        when(orderTypeService.getOrderTypesPage(any(), eq(DEFAULT_PAGEABLE))).thenReturn(expectedResponse);

        mvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(total))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.content[0].id").value(expectedFirstOrderType.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedFirstOrderType.getName()));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenThereAreElevenOrderTypesAndTheSecondPageIsRequested_whenGetOrderTypesPageIsPerformed_thenTheLastPageIsReturnedWithOneOrderType()
            throws Exception {
        int secondPage = 1;
        int total = 11;
        PageRequest pageRequest = PageRequest.of(secondPage, DEFAULT_PAGE_SIZE);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(pageRequest, total);
        OrderTypeResponse expectedFirstOrderType = expectedResponse.iterator().next();

        when(orderTypeService.getOrderTypesPage(any(), eq(pageRequest))).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("page", String.valueOf(secondPage)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.number").value(secondPage))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.content[0].id").value(expectedFirstOrderType.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedFirstOrderType.getName()));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenThereAreTwentyOneOrderTypesAndTheSecondPageIsRequested_whenGetOrderTypesPageIsPerformed_thenTheSecondPageIsReturnedAndAnotherOneIsAvailable()
            throws Exception {
        int secondPage = 1;
        int total = 21;
        PageRequest pageRequest = PageRequest.of(secondPage, DEFAULT_PAGE_SIZE);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(pageRequest, total);
        OrderTypeResponse expectedFirstOrderType = expectedResponse.iterator().next();

        when(orderTypeService.getOrderTypesPage(any(), eq(pageRequest))).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("page", String.valueOf(secondPage)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(DEFAULT_PAGE_SIZE))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(false))
                .andExpect(jsonPath("$.content[0].id").value(expectedFirstOrderType.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedFirstOrderType.getName()));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenThereAreElevenOrderTypesAndFourAreRequired_whenGetOrderTypesPageIsPerformed_thenTheFirstPageIsReturnedAndTwoMorePagesAreAvailable()
            throws Exception {
        int pageSize = 4;
        int total = 11;
        PageRequest pageRequest = PageRequest.of(FIRST_PAGE, pageSize);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(pageRequest, total);
        OrderTypeResponse expectedFirstOrderType = expectedResponse.iterator().next();

        when(orderTypeService.getOrderTypesPage(any(), eq(pageRequest))).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("size", String.valueOf(pageSize)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(pageSize))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(false))
                .andExpect(jsonPath("$.content[0].id").value(expectedFirstOrderType.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedFirstOrderType.getName()));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenThereAreElevenOrderTypesAndTheNextFourAreRequired_whenGetOrderTypesPageIsPerformed_thenTheSecondPageIsReturnedAndOneMorePageIsAvailable()
            throws Exception {
        int secondPage = 1;
        int pageSize = 4;
        int total = 11;
        PageRequest pageRequest = PageRequest.of(secondPage, pageSize);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(pageRequest, total);
        OrderTypeResponse expectedFirstOrderType = expectedResponse.iterator().next();

        when(orderTypeService.getOrderTypesPage(any(), eq(pageRequest))).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("size", String.valueOf(pageSize))
                .param("page", String.valueOf(secondPage)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(pageSize))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(false))
                .andExpect(jsonPath("$.content[0].id").value(expectedFirstOrderType.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedFirstOrderType.getName()));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenQueryParameter_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithIdOrNameContainingQueryAreReturned() throws Exception {
        int total = 5;
        String query = "A";
        OrderTypeRequestParams request = new OrderTypeRequestParams(query, null, null);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, total);

        when(orderTypeService.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("query", query)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(total))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(jsonPath("$.content[0].name").value(containsString(query)));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenNameParameter_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithNameContainingParameterAreReturned() throws Exception {
        int total = 4;
        String name = "A";
        OrderTypeRequestParams request = new OrderTypeRequestParams(null, null, name);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, total);

        when(orderTypeService.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("name", name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(total))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(jsonPath("$.content[0].name").value(containsString(name)));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenIdParameter_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithIdContainingParameterAreReturned() throws Exception {
        int total = 3;
        Long id = 10L;
        OrderTypeRequestParams request = new OrderTypeRequestParams(null, id, null);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, total);

        when(orderTypeService.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);

        mvc.perform(get(URL).param("id", String.valueOf(id))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(total))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(jsonPath("$.content[0].id").value(containsString(String.valueOf(id)), String.class));
    }

    @WithMockUser(roles="ORDERTYPE_GETALL")
    @Test
    void givenAllParameters_whenGetOrderTypesPageIsPerformed_thenOrderTypesWithIdOrNameContainingQueryAreReturned() throws Exception {
        int total = 6;
        String query = "2";
        Long id = 99L;
        String name = "Nonexistent";
        OrderTypeRequestParams request = new OrderTypeRequestParams(query, id, name);
        Page<OrderTypeResponse> expectedResponse = OrderTypes.mockedOrderTypes(request, DEFAULT_PAGEABLE, total);

        when(orderTypeService.getOrderTypesPage(request, DEFAULT_PAGEABLE)).thenReturn(expectedResponse);

        mvc.perform(get(URL)
                .param("query", query)
                .param("id", String.valueOf(id))
                .param("name", name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(total))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(jsonPath("$.content[0].id").value(containsString(query), String.class))
                .andExpect(jsonPath("$.content[0].name").value(containsString(query)));
    }
}