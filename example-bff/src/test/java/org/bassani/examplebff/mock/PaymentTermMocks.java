package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.PaymentTermRequest;
import org.bassani.examplemodellib.domain.response.PaymentTermInstallmentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class PaymentTermMocks {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static List<PaymentTermInstallmentResponse> mockedResponse() {

		PaymentTermInstallmentResponse paymentTerm1 =
                PaymentTermInstallmentResponse.builder().id(Long.valueOf(0)).conditionPaymentDescription("A Vista").daysQuantityPayment(0L).build();
		PaymentTermInstallmentResponse paymentTerm2 =
                PaymentTermInstallmentResponse.builder().id(Long.valueOf(1)).conditionPaymentDescription("10 DD").daysQuantityPayment(10L).build();
		PaymentTermInstallmentResponse paymentTerm3 =
                PaymentTermInstallmentResponse.builder().id(Long.valueOf(2)).conditionPaymentDescription("15 DD").daysQuantityPayment(15L).build();
		PaymentTermInstallmentResponse paymentTerm4 =
                PaymentTermInstallmentResponse.builder().id(Long.valueOf(3)).conditionPaymentDescription("20 DD").daysQuantityPayment(20L).build();
		PaymentTermInstallmentResponse paymentTerm5 =
                PaymentTermInstallmentResponse.builder().id(Long.valueOf(4)).conditionPaymentDescription("30 DD").daysQuantityPayment(30L).build();

		return List.of(paymentTerm1, paymentTerm2, paymentTerm3, paymentTerm4, paymentTerm5);
	}

	public static PaymentTermRequest mockedRequestName() {
		PaymentTermRequest request = PaymentTermRequest.builder().name("A Vista").build();
		return request;
	}

	public static Page<PaymentTermInstallmentResponse> mockedPaymentTerm(List<PaymentTermInstallmentResponse> allOrderTypes, Pageable pageable) {
		allOrderTypes = mockedResponse();
		return new PageImpl<>(allOrderTypes, pageable, allOrderTypes.size());
	}

	private static String safeWriteValueAsString(Object value) {
		String json = "";
		try {
			json = OBJECT_MAPPER.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return json;
	}

	public static String mockedPaymentTermAsJson(List<PaymentTermInstallmentResponse> mockedResponse, Pageable pageable) {
		return safeWriteValueAsString(mockedPaymentTerm(mockedResponse, pageable));
	}

}
