package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.PaymentTermMocks;
import org.bassani.examplebff.repository.PaymentTermRepository;
import org.bassani.examplemodellib.domain.request.PaymentTermRequest;
import org.bassani.examplemodellib.domain.response.PaymentTermInstallmentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTermServiceTest {

	@Mock
	private PaymentTermRepository repository;

	@InjectMocks
	private PaymentTermService service;

	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(1, 20);

	@Test
	void shallReturnAllPaymentTerms() {
		Page<PaymentTermInstallmentResponse> expected = new PageImpl<>(PaymentTermMocks.mockedResponse());

		when(repository.searchAllPaymentTerms(PaymentTermRequest.builder().build(),DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<PaymentTermInstallmentResponse> response = service.getAllPaymentTerms(PaymentTermRequest.builder().build(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected.getContent(), response);
	}

	@Test
	void shouldNotReturnPaymentTermsWhenTheListIsEmpty() {
		Page<PaymentTermInstallmentResponse> expected = new PageImpl<>(Collections.emptyList());

		when(repository.searchAllPaymentTerms(PaymentTermRequest.builder().build(),DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<PaymentTermInstallmentResponse> response = service.getAllPaymentTerms(PaymentTermRequest.builder().build(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected.getContent(), response);
	}

	@Test
	void shallReturnAllPaymentTermsFindAllByName() {
		Page<PaymentTermInstallmentResponse> expected = new PageImpl<>(PaymentTermMocks.mockedResponse());

		when(repository.searchAllPaymentTerms(PaymentTermMocks.mockedRequestName(), DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<PaymentTermInstallmentResponse> response = service.getAllPaymentTerms(PaymentTermMocks.mockedRequestName(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected.getContent(), response);
	}

}