package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.PaymentTermRepository;
import org.bassani.examplemodellib.domain.request.PaymentTermRequest;
import org.bassani.examplemodellib.domain.response.PaymentTermInstallmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentTermService {

	private final PaymentTermRepository paymentTermRepository;

	public Page<PaymentTermInstallmentResponse> getAllPaymentTerms(PaymentTermRequest request, Pageable pageable) {
		return paymentTermRepository.searchAllPaymentTerms(request, pageable);
	}
}
