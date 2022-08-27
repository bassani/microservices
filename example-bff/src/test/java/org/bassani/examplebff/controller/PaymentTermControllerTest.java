package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.PaymentTermMocks;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.PaymentTermService;
import org.bassani.examplemodellib.domain.request.PaymentTermRequest;
import org.bassani.examplemodellib.domain.response.PaymentTermInstallmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PaymentTermController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentTermControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PaymentTermService service;
    @MockBean private KeyCloakService keyCloakService;

	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(1, 20);
	private static final String ENDPOINT = "/purchases/paymentTerms";

    @WithMockUser(roles="PAYMENTTERM_GETALL")
	@Test
	void shouldReturnError404WhenTryingToSearchAllPaymentTermsWithIncorrectRequestMapping() throws Exception {

		Page<PaymentTermInstallmentResponse> expected = new PageImpl<>(PaymentTermMocks.mockedResponse());

		when(service.getAllPaymentTerms(PaymentTermRequest.builder().build(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get("/payment-terms").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

    @WithMockUser(roles="PAYMENTTERM_GETALL")
	@Test
	void shallReturnAllPaymentTerms() throws Exception {

		Page<PaymentTermInstallmentResponse> expected = new PageImpl<>(PaymentTermMocks.mockedResponse());

		when(service.getAllPaymentTerms(PaymentTermRequest.builder().build(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get(ENDPOINT).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

    @WithMockUser(roles="PAYMENTTERM_GETALL")
	@Test
	void shallReturnAllPaymentTermsWithRequestName() throws Exception {

		Page<PaymentTermInstallmentResponse> expected = new PageImpl<>(PaymentTermMocks.mockedResponse());

		when(service.getAllPaymentTerms(PaymentTermMocks.mockedRequestName(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get(ENDPOINT).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}