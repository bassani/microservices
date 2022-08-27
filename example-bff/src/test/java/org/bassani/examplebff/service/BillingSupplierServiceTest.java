package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.BillingSupplierRepository;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.BillingSupplierToPurchaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class BillingSupplierServiceTest {

	private final Integer PAGE = 0;
	private final Integer SIZE = 25;
	private final Sort    SORT = Sort.by("name").ascending();

	PageRequest page = PageRequest.of(PAGE, SIZE, SORT);

	@InjectMocks
	BillingSupplierService service;

	@Mock
	BillingSupplierRepository repository;

	@Mock
	private ObjectMapper mapper;

	@Test
	@DisplayName("Testing method service.getAllBillingSupplier - Return OK")
	void whenCallFindAllWithoutRequest_return_OK() throws IOException {
		BillingSupplierToPurchaseResponse manufacturer = BillingSupplierToPurchaseResponse.builder().id(2L).name("ACHE").build();
		List<BillingSupplierToPurchaseResponse> manufacturers = new ArrayList<>();
		ManufacturerRequest request = new ManufacturerRequest(2L, null, null, null, null, null, true);
		manufacturers.add(manufacturer);
		doReturn(manufacturers)
			.when(repository)
			.getAllBillingSupplier(page, request);

		assertNotNull(service.getAllBillingSupplier(request, page));
	}

}